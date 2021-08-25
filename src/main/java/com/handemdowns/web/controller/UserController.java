package com.handemdowns.web.controller;

import com.handemdowns.common.RestResponse;
import com.handemdowns.event.OnRegistrationCompleteEvent;
import com.handemdowns.persistence.dto.AccountSettingsDto;
import com.handemdowns.persistence.dto.UserForgotPasswordDto;
import com.handemdowns.persistence.dto.UserRegistrationDto;
import com.handemdowns.persistence.dto.UserSavePasswordDto;
import com.handemdowns.persistence.model.*;
import com.handemdowns.persistence.service.ISystemNotificationService;
import com.handemdowns.persistence.service.IUserService;
import com.handemdowns.service.IEmailService;
import com.handemdowns.validation.EmailExistsException;
import com.handemdowns.web.error.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@Controller
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private ApplicationEventPublisher eventPublisher;
    private IUserService userService;
    private UserDetailsService userDetailsService;
    private IEmailService emailService;
    private ISystemNotificationService systemNotificationService;

	@Value("${handemdowns.security.adminAccount}")
	private String adminAccount;

	@Autowired
	public UserController(ApplicationEventPublisher eventPublisher, IUserService userService, UserDetailsService userDetailsService,
						  IEmailService emailService, ISystemNotificationService systemNotificationService) {
		this.eventPublisher = eventPublisher;
		this.userService = userService;
		this.userDetailsService = userDetailsService;
		this.emailService = emailService;
		this.systemNotificationService = systemNotificationService;
	}

	@RequestMapping("/login")
    public String login() {
    	return "login";
    }

    @RequestMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "/error/401";
    }

    @RequestMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("newUserForm", new UserRegistrationDto());
        return "register";
    }

	@RequestMapping(value = "/check-user", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<User> checkUser(@RequestParam("email") String email) {
		User user = userService.findUserByEmail(email);
		if (user == null) {
			throw new UserNotFoundException();
		}
        return new RestResponse<>(RestResponse.SUCCESS, user);
	}

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<User> registerUserAccount(@Valid UserRegistrationDto userRegistrationDto) {
        User registered;
        try {
            registered = userService.registerNewUserAccount(userRegistrationDto);
        } catch (EmailExistsException e) {
        	throw new UserAlreadyExistsException();
        }
        log.info("Registered user account with information: {}", userRegistrationDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered));
        systemNotificationService.createNotification(SystemNotification.builder(SystemNotificationType.NEW_USER, "New user registered: " + registered.getEmail()).build());
        return new RestResponse<>(RestResponse.SUCCESS, registered);
    }

    @RequestMapping("/confirm-registration")
    public String showRegistrationConfirmationPage(@RequestParam("id") Long id, @RequestParam("token") String token, Model model) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
			throw new WebException(WebException.Strings.INVALID_TOKEN);
        }

        User user = verificationToken.getUser();
        if (!user.getId().equals(id)) {
			throw new WebException(WebException.Strings.EXPIRED_TOKEN);
        }

        if (user.getEnabled()) {
			throw new WebException(WebException.Strings.ERROR);
		}

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("regTokenExpired", true);
            model.addAttribute("token", token);
            return "message";
        }

        user.setEnabled(true);
        log.info("Confirmed user registration for: {}", user.getEmail());
        userService.saveRegisteredUser(user);
        return "redirect:/login?regSuccess";
    }
    
    @RequestMapping(value = "/resend-registration", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<VerificationToken> resendRegistrationToken(@RequestParam("token") String existingToken) throws MessagingException, IOException {
    	if (existingToken == null || existingToken.equals("null")) {
    		throw new InvalidTokenException();
    	}
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = userService.getUser(newToken.getToken());
		log.info("Resending registration token for: {}", user.getEmail());
        emailService.sendUserRegistrationEmail(user, newToken.getToken());
        return new RestResponse<>(RestResponse.SUCCESS, newToken);
    }

    @RequestMapping("/forgot")
    public String showForgotPasswordPage(Model model) {
        model.addAttribute("forgotPasswordForm", new UserForgotPasswordDto());
        return "forgot";
    }
    
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<User> resetPassword(@Valid UserForgotPasswordDto userForgotPasswordDto) throws MessagingException, IOException {
        User user = userService.findUserByEmail(userForgotPasswordDto.getEmail());
        if (user == null) {
        	throw new UserNotFoundException();
        } else {
        	if (!user.getEnabled()) {
        		throw new UserDisabledException();
        	}
        }

        // Do not allow admin account to reset password
		if (user.getEmail().equals(adminAccount)) {
			throw new UnauthorizedException();
		}

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
		log.info("Resetting user password for: {}", user.getEmail());
        emailService.sendResetPasswordEmail(user, token);
        return new RestResponse<>(RestResponse.SUCCESS, user);
    }

    @RequestMapping("/change-password")
    public String showChangePasswordPage(@RequestParam("id") Long id, @RequestParam("token") String token, Model model) {
        PasswordResetToken passToken = userService.getPasswordResetToken(token);
        if (passToken == null) {
			throw new WebException(WebException.Strings.INVALID_TOKEN);
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			throw new WebException(WebException.Strings.EXPIRED_TOKEN);
        }
        
        User user = passToken.getUser();
        if (!user.getId().equals(id)) {
			throw new WebException(WebException.Strings.ERROR);
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        model.addAttribute("savePasswordForm", new UserSavePasswordDto());
        return "update-password";
    }
    
    @RequestMapping(value = "/save-password", method = RequestMethod.POST)
    @PreAuthorize("hasRole('PERMISSION_USER')")
    public String savePassword(@Valid UserSavePasswordDto userSavePasswordDto, HttpServletRequest request, HttpServletResponse response) {
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	log.info("Changing user password for user: {}", user.getEmail());
        userService.changeUserPassword(user, userSavePasswordDto.getPassword());
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
            return "redirect:/login?updatePwSuccess";
		}

        return "redirect:/login?logout";
    }

    @RequestMapping("/account-settings")
    @PreAuthorize("hasRole('PERMISSION_USER')")
    public String showAccountSettingsPage(Model model) {
        model.addAttribute("accountSettingsForm", new AccountSettingsDto());
        return "accountsettings";
    }

    @RequestMapping(value = "/account-settings", method = RequestMethod.POST)
    @PreAuthorize("hasRole('PERMISSION_USER')")
    @ResponseBody
    public RestResponse<User> saveAccountSettings(@AuthenticationPrincipal UserDetails userDetails, @Valid AccountSettingsDto accountSettingsDto) {
        User user = userService.findUserByEmail(userDetails.getUsername());

        if (!StringUtils.isEmpty(accountSettingsDto.getPassword()) && !StringUtils.isEmpty(accountSettingsDto.getMatchingPassword())) {
            if (!userService.checkIfValidOldPassword(user, accountSettingsDto.getOldPassword())) {
                throw new InvalidOldPasswordException();
            }
			log.info("Changing user password for user: {}", user.getEmail());
            userService.changeUserPassword(user, accountSettingsDto.getPassword());
        }
        return new RestResponse<>(RestResponse.SUCCESS, user);
    }

    @RequestMapping("/watchlist")
    @PreAuthorize("hasRole('PERMISSION_USER')")
    public String showWatchlistPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        model.addAttribute("ads", user.getWatchlist());
        return "watchlist";
    }
}
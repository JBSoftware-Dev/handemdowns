package com.handemdowns.web.controller;

import com.handemdowns.common.RestResponse;
import com.handemdowns.persistence.dto.ContactUsDto;
import com.handemdowns.service.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class HelpController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private IEmailService emailService;

	@Autowired
	public HelpController(IEmailService emailService) {
		this.emailService = emailService;
	}

	@RequestMapping("/help")
    public String showHelpPage() {
        return "help/help";
    }

    @RequestMapping("/policies")
    public String showPoliciesPage() {
        return "redirect:/policies/terms-of-use";
    }

    @RequestMapping("/policies/terms-of-use")
    public String showTermsOfUsePage() {
        return "help/terms-of-use";
    }

    @RequestMapping("/policies/privacy-policy")
    public String showPrivacyPolicyPage() {
        return "help/privacy-policy";
    }

    @RequestMapping("/policies/cookies-policy")
    public String showCookiesPolicyPage() {
        return "help/cookies-policy";
    }

    @RequestMapping("/policies/anti-spam-policy")
    public String showAntiSpamPage() {
        return "help/anti-spam-policy";
    }

    @RequestMapping("/about-us")
    public String showAboutUsPage() {
        return "help/about-us";
    }

    @RequestMapping("/contact-us")
    public String showContactUsPage(Model model) {
        model.addAttribute("contactUsForm", new ContactUsDto());
        return "help/contact-us";
    }

    @RequestMapping(value = "/contact-us", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Object> sendContactUsMessage(@Valid ContactUsDto contactUsDto) throws IOException, MessagingException {
		log.info("Sening Contact Us email to support from: {}", contactUsDto.getEmail());
        emailService.sendContactUsEmail(contactUsDto);
        return new RestResponse<>(RestResponse.SUCCESS, null);
    }
}
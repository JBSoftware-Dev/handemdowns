package com.handemdowns.web.controller.advice;

import com.handemdowns.persistence.dto.AdSearchDto;
import com.handemdowns.persistence.model.Category;
import com.handemdowns.persistence.model.Location;
import com.handemdowns.persistence.model.User;
import com.handemdowns.persistence.service.ICategoryService;
import com.handemdowns.persistence.service.ILocationService;
import com.handemdowns.persistence.service.IUserService;
import com.handemdowns.service.IApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

@ControllerAdvice
public class DefaultControllerAdvice {
    private IApplicationService applicationService;
    private IUserService userService;
    private ICategoryService categoryService;
    private ILocationService locationService;

	@Autowired
	public DefaultControllerAdvice(IApplicationService applicationService, IUserService userService, ICategoryService categoryService, ILocationService locationService) {
		this.applicationService = applicationService;
		this.userService = userService;
		this.categoryService = categoryService;
		this.locationService = locationService;
	}

	@ModelAttribute("adImageUrl")
    public String adImageUrl() {
        return applicationService.getAdImageUrl();
    }

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute("adSearchForm")
    public AdSearchDto adSearchForm() {
        return new AdSearchDto();
    }

    @ModelAttribute("currentUser")
    public User currentUser(Authentication authentication) {
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof User) {
                return (User) authentication.getPrincipal();
            }

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userService.findUserByEmail(userDetails.getUsername());
        }
        return null;
    }

    @ModelAttribute("userCategory")
    public Category userCategory(HttpSession session) {
        if (session.getAttribute("user-category") != null) {
            return (Category) session.getAttribute("user-category");
        }
        return null;
    }

    @ModelAttribute("userLocation")
    public Location userLocation(HttpSession session, @CookieValue(value = "user-location", required = false) String cookieLocation) {
        if (session.getAttribute("user-location") != null) {
            return (Location) session.getAttribute("user-location");
        }
	    if (cookieLocation != null) {
            Location userLocation = locationService.findByCode(cookieLocation);
            if (userLocation != null) {
                session.setAttribute("user-location", userLocation);
                return userLocation;
            }
        }
        return null;
    }
}
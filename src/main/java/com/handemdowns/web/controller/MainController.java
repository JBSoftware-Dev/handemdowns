package com.handemdowns.web.controller;

import com.handemdowns.common.RestResponse;
import com.handemdowns.persistence.model.Ad;
import com.handemdowns.persistence.model.Category;
import com.handemdowns.persistence.model.Location;
import com.handemdowns.persistence.model.Status;
import com.handemdowns.persistence.service.IAdService;
import com.handemdowns.persistence.service.ICategoryService;
import com.handemdowns.persistence.service.ILocationService;
import com.handemdowns.web.error.GeoLocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {
	private IAdService adService;
	private ICategoryService categoryService;
	private ILocationService locationService;

	@Autowired
	public MainController(IAdService adService, ICategoryService categoryService, ILocationService locationService) {
		this.adService = adService;
		this.categoryService = categoryService;
		this.locationService = locationService;
	}

	@RequestMapping("/")
    public String showIndexPage(HttpSession session, Model model) {
		Location location = (Location) session.getAttribute("user-location");
		Slice<Ad> ads = adService.findByLocationAndStatus(location, Status.ACTIVE, new PageRequest(0, 12, Sort.Direction.DESC, "postDate"));
		model.addAttribute("ads", ads.getContent());
		model.addAttribute("hasMore", ads.hasNext());
    	return "index";
    }

	@RequestMapping(value = "/set-category", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<Category> setCategory(HttpSession session, @RequestParam(value = "category") String category) {
		Category userCategory = categoryService.findByCode(category);
		session.setAttribute("user-category", userCategory);
		return new RestResponse<>(RestResponse.SUCCESS, userCategory);
	}

	@RequestMapping(value = "/set-location", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<Location> setLocation(HttpServletResponse response, HttpSession session, @RequestParam(value = "location", required = false) String location,
								@RequestParam(value = "lat", required = false) Double lat, @RequestParam(value = "lon", required = false) Double lon) {
		Location userLocation = null;
		if (lat != null && lon != null) {
			userLocation = locationService.findNearestByCoordinates(lat, lon);
		} else if (location != null) {
			userLocation = locationService.findByCode(location);
			if (userLocation == null) {
				userLocation = locationService.findByName(location);
			}
		}

		if (userLocation != null) {
			Cookie locationCookie = new Cookie("user-location", userLocation.getCode());
			locationCookie.setMaxAge(31536000);
			response.addCookie(locationCookie);
			session.setAttribute("user-location", userLocation);
			return new RestResponse<>(RestResponse.SUCCESS, userLocation);
		}

		throw new GeoLocationException("Failed to set location");
	}

	@RequestMapping("/locations")
	public String showLocationPage(Model model) {
		model.addAttribute("canadianLocationTree", locationService.buildLocationTree("Canada"));
		model.addAttribute("americanLocationTree", locationService.buildLocationTree("United States"));
		return "location";
	}

	@RequestMapping("/message/ad-saved")
	public String showAdSavedMessage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		if (userDetails == null) {
			model.addAttribute("message", "Ad saved!  A confirmation email has been sent, check your email!");
		} else {
			model.addAttribute("message", "Ad saved!  It will be viewable shortly.");
		}

		return "message";
	}

	@RequestMapping("/message/ad-confirmed")
	public String showAdConfirmedMessage(Model model) {
		model.addAttribute("message", "Ad confirmed!  It will be viewable shortly.");
		return "message";
	}

	@RequestMapping("/message/ad-expired")
	public String showAdExpiredMessage(Model model) {
		model.addAttribute("message", "Ad has been expired, it is no longer viewable or editable.");
		return "message";
	}
}
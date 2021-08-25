package com.handemdowns.web.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handemdowns.common.MorrisDataObjects;
import com.handemdowns.common.RestResponse;
import com.handemdowns.geolocation.GeoLocation;
import com.handemdowns.persistence.model.MapPlot;
import com.handemdowns.persistence.model.Status;
import com.handemdowns.persistence.model.User;
import com.handemdowns.persistence.service.*;
import com.handemdowns.persistence.service.impl.MapPlotService;
import com.handemdowns.service.IEmailService;
import com.handemdowns.service.IGeoLocationService;
import com.handemdowns.web.error.GeoLocationException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('PERMISSION_ADMIN')")
public class AdminController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private ISystemNotificationService systemNotificationService;
    private IAdService adService;
    private IEmailService emailService;
    private IUserService userService;
	private ILocationService locationService;
	private IMapPlotService mapPlotService;
	private IGeoLocationService geoLocationService;

	@Value("${api.google.maps.key}")
	private String mapsKey;

	@Autowired
	public AdminController(ISystemNotificationService systemNotificationService, IUserService userService, IAdService adService, IEmailService emailService,
						   ILocationService locationService, MapPlotService mapPlotService, IGeoLocationService geoLocationService) {
		this.systemNotificationService = systemNotificationService;
		this.userService = userService;
		this.adService = adService;
		this.emailService = emailService;
		this.locationService = locationService;
		this.mapPlotService = mapPlotService;
		this.geoLocationService = geoLocationService;
	}

    @RequestMapping("")
    public String adminRoot() {
        return "redirect:/admin/home";
    }

    @RequestMapping("/home")
    public String showAdminHomepage(Model model) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		model.addAttribute("adCount", adService.countAllAds());
        model.addAttribute("userCount", userService.count());
        model.addAttribute("donations", "$10.00");
        model.addAttribute("supportTicketCount", 0);
		model.addAttribute("adGrowthData", mapper.writeValueAsString(getAdGrowthData()));
		model.addAttribute("reportedAds", adService.findByStatus(Status.REPORTED));
		model.addAttribute("userAdCountData", mapper.writeValueAsString(getUserAdCountData()));
        model.addAttribute("systemNotifications", systemNotificationService.getLatestNotifications());
        return "admin/acp-home";
    }

	@RequestMapping("/ads")
	public String showAdminAdsPage() {
		return "admin/acp-ads";
	}

	@RequestMapping("/users")
	public String showAdminAUsersPage(Model model) {
		model.addAttribute("users", userService.findAll());
		return "admin/acp-users";
	}

	@RequestMapping(value = "/enable-user/{id}", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<User> enableUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
		User caller = userService.findUserByEmail(userDetails.getUsername());
		User actioned = userService.getUserByID(id);
		log.info("Admin user: {} ### Enabling user: {}", caller.getEmail(), actioned.getEmail());
		userService.saveRegisteredUser(actioned);
		return new RestResponse<>(RestResponse.SUCCESS, actioned);
	}

	@RequestMapping(value = "/disable-user/{id}", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<User> disableUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
		User caller = userService.findUserByEmail(userDetails.getUsername());
		User actioned = userService.getUserByID(id);
		log.info("Admin user: {} ### Disabling user: {}", caller.getEmail(), actioned.getEmail());
		userService.disableRegisteredUser(actioned);
		return new RestResponse<>(RestResponse.SUCCESS, actioned);
	}

	@RequestMapping(value = "/promote-user/{id}", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<User> promoteUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
		User caller = userService.findUserByEmail(userDetails.getUsername());
		User actioned = userService.getUserByID(id);
		log.info("Admin user: {} ### Promoting user: {}", caller.getEmail(), actioned.getEmail());
		userService.promteToAdmin(actioned);
		return new RestResponse<>(RestResponse.SUCCESS, actioned);
	}

	@RequestMapping("/locations")
	public String showAdminLocationsPage(Model model) {
		model.addAttribute("canadianLocations", locationService.findByCountry("Canada"));
		model.addAttribute("americanLocations", locationService.findByCountry("United States"));
		model.addAttribute("mapPlots", mapPlotService.findAll());
		model.addAttribute("mapsKey", mapsKey);
		return "admin/acp-locations";
	}

	@RequestMapping(value = "/reindex-locations", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<Object> reindexLocations() {
		log.info("Reindexing MapPlots");
		return new RestResponse<>(RestResponse.SUCCESS, null);
	}

	@RequestMapping(value = "/geolocation-test")
	@ResponseBody
	public RestResponse<GeoLocationTestResults> geoLocationTest(@RequestParam(value = "lat") String lat, @RequestParam(value = "lon") String lon) {
		if (lat.isEmpty() || lon.isEmpty()) {
			throw new GeoLocationException("Latitude and longitude must be entered");
		}

		log.info("######################################### GEOLOCATION TEST #########################################");
		log.info("User input: lat: {}, lon: {}", lat, lon);
		GeoLocationTestResults results = new GeoLocationTestResults();

		GeoLocation geoLocation = new GeoLocation("N/A", "N/A", Double.valueOf(lat), Double.valueOf(lon));
		results.setGeoLocation(geoLocation);
		log.info("GeoLocation of user: {}", geoLocation);

		log.info("Searching for closest MapPlots sorted by distance");
		List<MapPlot> closestMapPlots = mapPlotService.findClosestMapPlotsByRadius(geoLocation.getLatitude(), geoLocation.getLongitude(), 100, 10);
		closestMapPlots.forEach(i -> log.info("Found {}", i));
		results.setFoundMapPlots(closestMapPlots);
		if (closestMapPlots.size() > 0) {
			log.info("The closest MapPlot is {}", closestMapPlots.get(0));
			results.setClosest(closestMapPlots.get(0));
		}

		log.info("####################################### END GEOLOCATION TEST #######################################");
		return new RestResponse<>(RestResponse.SUCCESS, results);
	}

	@RequestMapping(value = "/postal-code-test")
	@ResponseBody
	public RestResponse<GeoLocationTestResults> postalCodeTest(@RequestParam(value = "postalCode") String postalCode) {
		if (postalCode.isEmpty()) {
			throw new GeoLocationException("Postal code must be entered");
		}

		log.info("######################################### POSTAL CODE TEST #########################################");
		log.info("User input: postalCode: {}", postalCode);
		GeoLocationTestResults results = new GeoLocationTestResults();

		GeoLocation geoLocation = geoLocationService.findGeoLocationByPostalCode(postalCode);
		results.setGeoLocation(geoLocation);
		log.info("GeoLocation of user: {}", geoLocation);

		log.info("Searching for closest MapPlots sorted by distance");
		List<MapPlot> closestMapPlots = mapPlotService.findClosestMapPlotsByRadius(geoLocation.getLatitude(), geoLocation.getLongitude(), 100, 10);
		closestMapPlots.forEach(i -> log.info("Found {}", i));
		results.setFoundMapPlots(closestMapPlots);
		if (closestMapPlots.size() > 0) {
			log.info("The closest MapPlot is {}", closestMapPlots.get(0));
			results.setClosest(closestMapPlots.get(0));
		}

		log.info("####################################### END POSTAL CODE TEST #######################################");
		return new RestResponse<>(RestResponse.SUCCESS, results);
	}

	@RequestMapping("/mail")
	public String showAdminMailPage() {
		return "admin/acp-mail";
	}

	@RequestMapping(value = "/send-test-email", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse sendAdEmail(@RequestParam(value = "email") String email) throws MessagingException, IOException {
		log.info("Sending test email to {}", email);
		emailService.sendTestEmail(email);
		return new RestResponse<>(RestResponse.SUCCESS, null);
	}

	@RequestMapping("/server-log")
	public String showAdminLogPage() {
		return "admin/acp-log";
	}

    private List<MorrisDataObjects.AdGrowthChartPlot> getAdGrowthData() {
		List<MorrisDataObjects.AdGrowthChartPlot> data = new ArrayList<>();
		data.add(new MorrisDataObjects.AdGrowthChartPlot("2015 Q1", 0L));
		data.add(new MorrisDataObjects.AdGrowthChartPlot("2015 Q2", 0L));
		data.add(new MorrisDataObjects.AdGrowthChartPlot("2015 Q3", 0L));
		data.add(new MorrisDataObjects.AdGrowthChartPlot("2015 Q4", 0L));
		data.add(new MorrisDataObjects.AdGrowthChartPlot("2016 Q1", 0L));
		data.add(new MorrisDataObjects.AdGrowthChartPlot("2016 Q2", 0L));
		data.add(new MorrisDataObjects.AdGrowthChartPlot("2016 Q3", 0L));
		data.add(new MorrisDataObjects.AdGrowthChartPlot("2016 Q4", adService.countAllAds()));
		return data;
	}

	private List<MorrisDataObjects.UserAdCountChartPlot> getUserAdCountData() {
		List<MorrisDataObjects.UserAdCountChartPlot> data = new ArrayList<>();
		data.add(new MorrisDataObjects.UserAdCountChartPlot("Anonymous Ads", adService.countAnonymousAds()));
		data.add(new MorrisDataObjects.UserAdCountChartPlot("User Ads", adService.countUserAds()));
		return data;
	}

	@Data
	private class GeoLocationTestResults {
		private GeoLocation geoLocation;
		private List<MapPlot> foundMapPlots;
		private MapPlot closest;
	}
}
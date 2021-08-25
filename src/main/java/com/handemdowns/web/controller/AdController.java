package com.handemdowns.web.controller;

import com.google.common.collect.Lists;
import com.handemdowns.common.RestResponse;
import com.handemdowns.common.util.StringUtil;
import com.handemdowns.event.OnAnonymousAdCreatedEvent;
import com.handemdowns.persistence.dao.specification.AdSpecification;
import com.handemdowns.persistence.dto.AdCreateDto;
import com.handemdowns.persistence.dto.AdSearchDto;
import com.handemdowns.persistence.dto.AdSendEmailDto;
import com.handemdowns.persistence.model.*;
import com.handemdowns.persistence.service.IAdService;
import com.handemdowns.persistence.service.ICategoryService;
import com.handemdowns.persistence.service.ILocationService;
import com.handemdowns.persistence.service.IUserService;
import com.handemdowns.service.IEmailService;
import com.handemdowns.web.error.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Controller
public class AdController {
    private static final int SEARCH_PAGE_SIZE = 12;
    private final Logger log = LoggerFactory.getLogger(getClass());

	private IAdService adService;
    private ApplicationEventPublisher eventPublisher;
    private IUserService userService;
	private ICategoryService categoryService;
    private ILocationService locationService;
    private IEmailService emailService;

    @Value("${api.google.maps.key}")
    private String mapsKey;

	@Autowired
	public AdController(IAdService adService, ApplicationEventPublisher eventPublisher, IUserService userService,
						ICategoryService categoryService, ILocationService locationService, IEmailService emailService) {
		this.adService = adService;
		this.eventPublisher = eventPublisher;
		this.userService = userService;
		this.categoryService = categoryService;
		this.locationService = locationService;
		this.emailService = emailService;
	}

	@RequestMapping("/category/{categoryCode}")
    public String showCategoryPage(HttpSession session, @PathVariable String categoryCode, Model model) {
        Category category = categoryService.findByCode(categoryCode);
        Location location = (Location) session.getAttribute("user-location");
        if ((category == null || location == null) && !categoryCode.equals("ALL")) {
            return "redirect:/";
        }

        Slice<Ad> ads;
        if (category == null) {
            ads = adService.findByLocationAndStatus(location, Status.ACTIVE, new PageRequest(0, 24, Sort.Direction.DESC, "postDate"));
        } else {
            ads = adService.findByCategoryAndLocationAndStatus(category, location, Status.ACTIVE, new PageRequest(0, 24, Sort.Direction.DESC, "postDate"));
            model.addAttribute("category", category);
        }

        model.addAttribute("ads", ads.getContent());
        model.addAttribute("hasMore", ads.hasNext());
        return "category";
    }

    @RequestMapping("/search")
    public String showSearchPage(Model model, @Valid AdSearchDto adSearchDto,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "area", required = false) String area,
                                 @RequestParam(value = "willDeliver", required = false) Boolean willDeliver) throws UnsupportedEncodingException {
        String searchTopic = adSearchDto.getTopic();
        Category searchCategory = categoryService.findByCode(adSearchDto.getCategoryCode());
        Location searchLocation = locationService.findByCode(adSearchDto.getLocationCode());
        if (area != null) {
            Location searchArea = locationService.findByArea(URLDecoder.decode(area, "UTF-8"));
            if (searchArea != null) {
                searchLocation = searchArea;
            }
        }

        if (searchTopic == null || searchLocation == null || page <= 0) {
            return "redirect:/";
        }
        if (searchLocation.getArea() != null) {
            List<Location> areas = locationService.findAreasByLocation(searchLocation);
            model.addAttribute("areas", areas);
        }

        Specification<Ad> searchSpecification = AdSpecification.search(searchTopic, searchCategory, searchLocation, willDeliver);
        Page<Ad> ads = adService.search(searchSpecification, adsSlice(page - 1, SEARCH_PAGE_SIZE));

        model.addAttribute("ads", ads.getContent());
        model.addAttribute("currentPage", page - 1);
        model.addAttribute("items", ads.getTotalElements());
        return "search";
    }

    @RequestMapping("/ad/{id}")
    public String showAdDetailsPage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, Model model) {
        Optional<Ad> found = adService.findById(id);
        if (found.isPresent()) {
            if (found.get().getStatus() != Status.ACTIVE) {
                throw new WebException(WebException.Strings.AD_NOT_ACTIVE);
            }
        } else {
            throw new WebException(WebException.Strings.AD_NOT_FOUND);
        }

        if (userDetails != null) {
            User user = userService.findUserByEmail(userDetails.getUsername());
            List<Ad> watchedAds = Lists.newArrayList(user.getWatchlist());
            if (watchedAds.stream().anyMatch(watchedAd -> watchedAd.getId().equals(id))) {
                model.addAttribute("watching", true);
            } else {
                model.addAttribute("watching", false);
            }
        }

        Slice<Ad> relatedAds = adService.getRelatedAds(found.get().getCategory(), Status.ACTIVE, found.get().getId(), adsSlice(0, 4));
        model.addAttribute("ad", found.get());
        model.addAttribute("ads", relatedAds.getContent());
        model.addAttribute("sendAdEmailForm", new AdSendEmailDto());
        model.addAttribute("mapsKey", mapsKey);
        return "details";
    }

    @RequestMapping("/data/{type}")
    public String getRelatedAds(HttpSession session, @PathVariable String type, @RequestParam(value = "page") int page,
                                @RequestParam(value = "categoryCode", required = false) String categoryCode,
                                Model model) {
        Category category = (Category) session.getAttribute("user-category");
        Location location = (Location) session.getAttribute("user-location");
        if (location == null) {
            return "error";
        }

        Slice<Ad> ads;
        switch (type) {
            case"nearby-ads":
                ads = adService.findByLocationAndStatus(location, Status.ACTIVE, adsSlice(page, SEARCH_PAGE_SIZE));
                break;
            case"category-nearby-ads":
                if (category == null || categoryCode.equals("ALL")) {
                    ads = adService.findByLocationAndStatus(location, Status.ACTIVE, adsSlice(page, SEARCH_PAGE_SIZE));
                } else {
                    ads = adService.findByCategoryAndLocationAndStatus(category, location, Status.ACTIVE, adsSlice(page, SEARCH_PAGE_SIZE));
                }
                break;
            default:
                throw new NoMoreDataException();
        }

        if (!ads.hasContent()) {
            throw new NoMoreDataException();
        }
        model.addAttribute("ads", ads.getContent());
        return "fragments/ads :: ad-panels";
    }

    @RequestMapping("/print-ad")
    public String showAdPrintPage(@RequestParam(value = "ad") Long ad, Model model) {
        Optional<Ad> found = adService.findById(ad);
        if (found.isPresent()) {
            if (found.get().getStatus() != Status.ACTIVE) {
                throw new WebException(WebException.Strings.AD_NOT_ACTIVE);
            }
        } else {
            throw new WebException(WebException.Strings.AD_NOT_FOUND);
        }

        model.addAttribute("ad", found.get());
        return "print";
    }

    @RequestMapping(value = "/send-ad-email", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Ad> sendAdEmail(@Valid AdSendEmailDto adSendEmailDto) throws MessagingException, IOException {
        Optional<Ad> found = adService.findById(adSendEmailDto.getAdId());
        if (found.isPresent()) {
            if (found.get().getStatus() != Status.ACTIVE) {
                throw new AdNotActiveException();
            }
			log.info("Sending reply email: {}", adSendEmailDto);
            emailService.sendAdReplyEmail(found.get(), adSendEmailDto);
        } else {
            throw new AdNotFoundException();
        }
        return new RestResponse<>(RestResponse.SUCCESS, found.get());
    }

    @RequestMapping("/create-ad")
    public String showCreateAdPage(HttpSession session, Model model) {
        if (session.getAttribute("user-location") != null) {
            model.addAttribute("areas", locationService.findAreasByLocation((Location) session.getAttribute("user-location")));
        }
        model.addAttribute("createAdForm", new AdCreateDto());
        return "createad";
    }

    @RequestMapping("/edit-ad/{id}")
    public String showEditAdPage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestParam(value = "token", required = false) String token, Model model) {
        Optional<Ad> editable = getCheckedAd(userDetails, id, token);
        if (editable.isPresent()) {
            if (editable.get().getStatus() != Status.ACTIVE && editable.get().getStatus() != Status.HOLD ) {
                throw new WebException(WebException.Strings.AD_NOT_EDITABLE);
            }
        } else {
            throw new WebException(WebException.Strings.UNAUTHORIZED);
        }
        model.addAttribute("ad", editable.get());
        model.addAttribute("createAdForm", new AdCreateDto());
        return "createad";
    }

    @RequestMapping(value = "/save-ad", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Ad> saveAd(@AuthenticationPrincipal UserDetails userDetails, MultipartHttpServletRequest request, @Valid AdCreateDto adCreateDto, @RequestParam(value = "id", required = false) Long id,
						   @RequestParam(value = "token", required = false) String token, @RequestParam(value = "removeImages", required = false) String[] removeImages) throws IOException {
        User user = null;
        if (userDetails != null) {
            user = userService.findUserByEmail(userDetails.getUsername());
        }

        Ad savedAd;
        Optional<Ad> editable = getCheckedAd(userDetails, id, token);
        if (editable.isPresent()) {
            if (editable.get().getStatus() != Status.ACTIVE && editable.get().getStatus() != Status.HOLD ) {
                throw new AdExpiredException();
            }

            Map<String, MultipartFile> fileMap = request.getFileMap();
            List<MultipartFile> multipartFiles = new ArrayList<>(fileMap.values());
            log.info("Editing Ad: {} with images: {}", adCreateDto, StringUtil.printMultipartFiles(multipartFiles));
            savedAd = adService.editAd(editable.get(), adCreateDto, multipartFiles, Arrays.asList(removeImages));
        } else {
            Map<String, MultipartFile> fileMap = request.getFileMap();
            List<MultipartFile> multipartFiles = new ArrayList<>(fileMap.values());
            log.info("Creating Ad: {} with images: {}", adCreateDto, StringUtil.printMultipartFiles(multipartFiles));
            savedAd = adService.createNewAd(adCreateDto, multipartFiles, user);
            if (savedAd.isAnnonymous()) {
                eventPublisher.publishEvent(new OnAnonymousAdCreatedEvent(savedAd));
            }
        }
        return new RestResponse<>(RestResponse.SUCCESS, savedAd);
    }

    @RequestMapping("/confirm-ad/{id}")
    public String confirmAd(@PathVariable Long id, @RequestParam(value = "token") String token) {
        Optional<Ad> editable = getCheckedAd(null, id, token);
        if (editable.isPresent()) {
            if (editable.get().getStatus() == Status.AWAITING_APPROVAL) {
                log.info("Confirming Ad: {}", editable.get());
                adService.confirm(editable.get());
            } else {
                throw new WebException(WebException.Strings.LINK_NOT_ACTIVE);
            }
        } else {
            throw new WebException(WebException.Strings.INVALID_TOKEN);
        }
        return "redirect:/message/ad-confirmed";
    }

    @RequestMapping("/my-ads")
    @PreAuthorize("hasRole('PERMISSION_USER')")
    public String showMyAdsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        List<Ad> ads = adService.findByUserAndStatus(user, Status.ACTIVE);
        model.addAttribute("ads", ads);
        return "myads";
    }

    @RequestMapping("/my-data/{type}")
    @PreAuthorize("hasRole('PERMISSION_USER')")
    public String getMyAdsData(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String type, Model model) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        List<Ad> ads;

        switch (type) {
            case"active":
                ads = adService.findByUserAndStatus(user, Status.ACTIVE);
                break;
            case"onhold":
                ads = adService.findByUserAndStatus(user, Status.HOLD);
                break;
            case"pending":
                ads = adService.findByUserAndStatus(user, Status.PENDING);
                break;
            default:
                throw new NoMoreDataException();
        }

        model.addAttribute("ads", ads);
        model.addAttribute("type", type);
        return "fragments/ads :: myads";
    }

    @RequestMapping(value = "/hold-ad/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('PERMISSION_USER')")
    @ResponseBody
    public RestResponse<Ad> holdAd(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Optional<Ad> editable = getCheckedAd(userDetails, id, null);
        if (editable.isPresent()) {
            if (editable.get().getStatus() != Status.ACTIVE && editable.get().getStatus() != Status.HOLD ) {
                throw new AdExpiredException();
            }
            adService.hold(editable.get());
        } else {
            throw new UnauthorizedException();
        }
        return new RestResponse<>(RestResponse.SUCCESS, editable.get());
    }

    @RequestMapping(value = "/activate-ad/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('PERMISSION_USER')")
    @ResponseBody
    public RestResponse<Ad> activateAd(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Optional<Ad> editable = getCheckedAd(userDetails, id, null);
        if (editable.isPresent()) {
            if (editable.get().getStatus() != Status.ACTIVE && editable.get().getStatus() != Status.HOLD ) {
                throw new AdExpiredException();
            }
            adService.activate(editable.get());
        } else {
            throw new UnauthorizedException();
        }
        return new RestResponse<>(RestResponse.SUCCESS, editable.get());
    }

    @RequestMapping(value = "/expire-ad", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Ad> expireAd(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "id") Long id, @RequestParam(value = "token", required = false) String token) {
        Optional<Ad> editable = getCheckedAd(userDetails, id, token);
        if (editable.isPresent()) {
            if (editable.get().getStatus() != Status.ACTIVE && editable.get().getStatus() != Status.HOLD ) {
                throw new AdExpiredException();
            }
            log.info("Expiring Ad: {}", editable.get());
            adService.expire(editable.get());
        } else {
            if (userDetails != null) {
                throw new UnauthorizedException();
            } else {
                throw new InvalidTokenException();
            }
        }
        return new RestResponse<>(RestResponse.SUCCESS, editable.get());
    }

    @RequestMapping(value = "/watch-ad", method = RequestMethod.POST)
    @PreAuthorize("hasRole('PERMISSION_USER')")
    @ResponseBody
    public RestResponse<Ad> watchAd(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "id") Long id) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        Optional<Ad> found = adService.findById(id);
        if (found.isPresent()) {
            if (found.get().getStatus() != Status.ACTIVE && found.get().getStatus() != Status.HOLD ) {
                throw new AdExpiredException();
            }
            userService.watchAd(user, found.get());
        } else {
            throw new AdNotFoundException();
        }
        return new RestResponse<>(RestResponse.SUCCESS, found.get());
    }

    @RequestMapping(value = "/unwatch-ad", method = RequestMethod.POST)
    @PreAuthorize("hasRole('PERMISSION_USER')")
    @ResponseBody
    public RestResponse<Ad> unwatchAd(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "id") Long id) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        Optional<Ad> found = adService.findById(id);
        if (found.isPresent()) {
            if (found.get().getStatus() != Status.ACTIVE && found.get().getStatus() != Status.HOLD ) {
                throw new AdExpiredException();
            }
            userService.unwatchAd(user, found.get());
        } else {
            throw new AdNotFoundException();
        }
        return new RestResponse<>(RestResponse.SUCCESS, found.get());
    }

    @RequestMapping(value = "/report-ad", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Ad> reportAd(HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "id") Long id, @RequestParam(value = "reason") String reason) {
        String reportedBy;
        if (userDetails != null) {
            reportedBy = userDetails.getUsername();
        } else {
            reportedBy = StringUtil.getUserIp(request);
        }

        Optional<Ad> editable = adService.findById(id);
        if (editable.isPresent()) {
            AdReport report = AdReport.builder(editable.get(), reason, reportedBy).build();
            log.info("Reporting Ad with report: {}", report);
            adService.report(report);
        } else {
            throw new AdNotFoundException();
        }
        return new RestResponse<>(RestResponse.SUCCESS, editable.get());
    }

    private Optional<Ad> getCheckedAd(UserDetails userDetails, Long id, String token) {
        Optional<Ad> editable;
        if (userDetails == null) {
            editable = adService.findByToken(token);
        } else {
            User user = userService.findUserByEmail(userDetails.getUsername());
            editable = adService.findByUser(user).stream().filter(ad -> ad.getId().equals(id)).findFirst();
        }
        return editable;
    }

    private Pageable adsSlice(int page, int size) {
        return new PageRequest(page, size, Sort.Direction.DESC, "postDate");
    }
}
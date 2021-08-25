package com.handemdowns.persistence.service.impl;

import com.handemdowns.geolocation.GeoLocation;
import com.handemdowns.persistence.dao.AdReportRepository;
import com.handemdowns.persistence.dao.AdRepository;
import com.handemdowns.persistence.dao.CategoryRepository;
import com.handemdowns.persistence.dto.AdCreateDto;
import com.handemdowns.persistence.model.*;
import com.handemdowns.persistence.service.IAdService;
import com.handemdowns.persistence.service.ILocationService;
import com.handemdowns.service.IApplicationService;
import com.handemdowns.service.IGeoLocationService;
import com.handemdowns.service.IStorageService;
import com.handemdowns.service.IThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class AdService implements IAdService {
	private AdRepository repository;
	private AdReportRepository adReportRepository;
	private CategoryRepository categoryRepository;
	private ILocationService locationService;
	private IApplicationService applicationService;
	private IStorageService storageService;
	private IThumbnailService thumbnailService;
	private IGeoLocationService geoLocationService;

	@Autowired
	public AdService(AdRepository repository, AdReportRepository adReportRepository, CategoryRepository categoryRepository, ILocationService locationService,
					 IApplicationService applicationService, IStorageService storageService, IThumbnailService thumbnailService, IGeoLocationService geoLocationService) {
		this.repository = repository;
		this.adReportRepository = adReportRepository;
		this.categoryRepository = categoryRepository;
		this.locationService = locationService;
		this.applicationService = applicationService;
		this.storageService = storageService;
		this.thumbnailService = thumbnailService;
		this.geoLocationService = geoLocationService;
	}

	@Override
	public Ad createNewAd(AdCreateDto adCreateDto, List<MultipartFile> multipartFiles, User user) throws IOException {
		Ad ad = Ad.builder(user, UUID.randomUUID().toString()).build();
		ad = provisionAd(ad, adCreateDto, multipartFiles, null);
		if (ad.isAnnonymous()) {
			ad.setStatus(Status.AWAITING_APPROVAL);
		} else {
			ad.setStatus(Status.PENDING);
		}

		return repository.save(ad);
	}

	@Override
	public Ad editAd(Ad ad, AdCreateDto adCreateDto, List<MultipartFile> multipartFiles, List<String> imgRemovalList) throws IOException {
		ad = provisionAd(ad, adCreateDto, multipartFiles, imgRemovalList);
		ad.setStatus(Status.PENDING);

		return repository.save(ad);
	}

	@Override
	public Optional<Ad> findById(Long id) {
		return Optional.ofNullable(repository.findOne(id));
	}

	@Override
	public Optional<Ad> findByToken(String token) {
		return repository.findByToken(token);
	}

	@Override
	public List<Ad> findByUser(User user) {
		return repository.findAllByUser(user);
	}

	@Override
	public List<Ad> findByStatus(Status status) {
		return repository.findAllByStatus(status);
	}

	@Override
	public List<Ad> findByUserAndStatus(User user, Status status) {
		return repository.findAllByUserAndStatus(user, status);
	}

	@Override
	public Slice<Ad> findByLocationAndStatus(Location location, Status status, Pageable pageable) {
		return repository.findAllByLocationAndStatus(location, status, pageable);
	}

    @Override
    public Slice<Ad> findByCategoryAndLocationAndStatus(Category category, Location location, Status status, Pageable pageable) {
        return repository.findAllByCategoryAndLocationAndStatus(category, location, status, pageable);
    }

	@Override
	public Slice<Ad> getRelatedAds(Category category, Status status, Long id, Pageable pageable) {
		return repository.findAllByCategoryAndStatusAndNotId(category, status, id, pageable);
	}

    @Override
	public Page<Ad> search(Specification<Ad> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public Long countAllAds() {
		return repository.count();
	}

	@Override
	public Long countUserAds() {
		return countAllAds() - countAnonymousAds();
	}

	@Override
	public Long countAnonymousAds() {
		return repository.countByUser(null);
	}

	@Override
	public void confirm(Ad ad) {
		ad.setStatus(Status.PENDING);
		repository.save(ad);
	}

	@Override
	public void activate(Ad ad) {
		ad.setStatus(Status.ACTIVE);
		ad.setPostDate(new Date());
		repository.save(ad);
	}

	@Override
	public void hold(Ad ad) {
		ad.setStatus(Status.HOLD);
        ad.setHoldDate(new Date());
		repository.save(ad);
	}

	@Override
	public void expire(Ad ad) {
		ad.setExpiryDate(new Date());
		ad.setStatus(Status.EXPIRED);
		repository.save(ad);
	}

	@Override
	public void report(AdReport adReport) {
		adReportRepository.save(adReport);
		Ad ad = adReport.getAd();
		ad.incrementReportCount();

		if (ad.getReportCount() >= 10) {
			ad.setStatus(Status.REPORTED);
		}
		repository.save(ad);
	}

	@Override
	public void delete(Ad ad) {
		repository.delete(ad);
	}

	private Ad provisionAd(Ad ad, AdCreateDto adCreateDto, List<MultipartFile> multipartFiles, List<String> imgRemovalList) throws IOException {
		ad.setTitle(adCreateDto.getTitle());
		ad.setDescription(adCreateDto.getDescription());
		ad.setCategory(categoryRepository.findByCode(adCreateDto.getCategoryCode()));
		ad.setPostalCode(adCreateDto.getPostalCode().toUpperCase().replaceAll(" ", ""));
		ad.setLocation(adCreateDto.getLocationCode() == null ? findLocationByPostalCode(adCreateDto.getPostalCode()) : locationService.findByCode(adCreateDto.getLocationCode()));
		ad.setWillDeliver(adCreateDto.getWillDeliver() == null ? false : adCreateDto.getWillDeliver());
		ad.setEmail(adCreateDto.getEmail());

		if (imgRemovalList != null) {
			imgRemovalList.forEach(i -> {
                storageService.delete(applicationService.getS3AdImageDirectory() + i);
                ad.removeImage(findAdImage(ad, i));
            });
		}

		if (multipartFiles != null) {
			List<Image> images = uploadWithThumbnails(ad, multipartFiles);
			images.forEach(ad::addImage);
		}
		return ad;
	}

	private List<Image> upload(Ad ad, List<MultipartFile> multipartFiles) throws IOException {
		List<Image> images = new ArrayList<>();
		for (MultipartFile multipartFile : multipartFiles) {
			String directory = applicationService.getS3AdImageDirectory();
			String filename = UUID.randomUUID().toString();
			String extension = multipartFile.getContentType().replace("image/", "");
			if (extension.equals("jpeg")) extension = "jpg";
			storageService.upload(multipartFile, directory + filename + "." + extension);
			images.add(Image.builder(filename, extension, multipartFile.getContentType(), multipartFile.getSize(), ad).build());
		}
		return images;
	}

	private List<Image> uploadWithThumbnails(Ad ad, List<MultipartFile> multipartFiles) throws IOException {
		List<Image> images = new ArrayList<>();
		for (MultipartFile multipartFile : multipartFiles) {
			String directory = applicationService.getS3AdImageDirectory();
			String filename = UUID.randomUUID().toString();
			String extension = multipartFile.getContentType().replace("image/", "");
			if (extension.equals("jpeg")) extension = "jpg";
			storageService.upload(multipartFile, directory + filename + "." + extension);
			images.add(Image.builder(filename, extension, multipartFile.getContentType(), multipartFile.getSize(), ad).build());

			BufferedImage thumbnail1 = thumbnailService.resize(multipartFile.getBytes(), 200, 120);
			storageService.upload(thumbnail1, extension, multipartFile.getContentType(), directory + filename + "_t1" + "." + extension);
		}
		return images;
	}

	private Image findAdImage(Ad ad, String key) {
		Optional<Image> found = ad.getImages().stream().filter(image -> image.getFilename().equals(key)).findFirst();
		return found.orElse(null);
	}

	private Location findLocationByPostalCode(String postalCode) {
		GeoLocation geoLocation = geoLocationService.findGeoLocationByPostalCode(postalCode);
		return locationService.findNearestByCoordinates(geoLocation.getLatitude(), geoLocation.getLongitude());
	}
}
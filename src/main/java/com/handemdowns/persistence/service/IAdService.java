package com.handemdowns.persistence.service;

import com.handemdowns.persistence.dto.AdCreateDto;
import com.handemdowns.persistence.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IAdService {
	Ad createNewAd(AdCreateDto adDto, List<MultipartFile> multipartFiles, User user) throws IOException;
	Ad editAd(Ad ad, AdCreateDto adDto, List<MultipartFile> multipartFiles, List<String> imgRemovalList) throws IOException;

	Optional<Ad> findById(Long id);
	Optional<Ad> findByToken(String token);

	List<Ad> findByUser(User user);
	List<Ad> findByStatus(Status status);
	List<Ad> findByUserAndStatus(User user, Status status);

	Slice<Ad> findByLocationAndStatus(Location location, Status status, Pageable pageable);
    Slice<Ad> findByCategoryAndLocationAndStatus(Category category, Location location, Status status, Pageable pageable);
	Slice<Ad> getRelatedAds(Category category, Status status, Long id, Pageable pageable);
	Page<Ad> search(Specification<Ad> specification, Pageable pageable);

	Long countAllAds();
	Long countUserAds();
	Long countAnonymousAds();

	void confirm(Ad ad);
	void activate(Ad ad);
	void hold(Ad ad);
	void expire(Ad ad);
	void report(AdReport adReport);

	void delete(Ad ad);
}
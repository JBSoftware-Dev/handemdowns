package com.handemdowns.persistence.dao;

import com.handemdowns.persistence.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AdRepository extends JpaRepository<Ad, Long>, JpaSpecificationExecutor<Ad> {
    Optional<Ad> findByToken(String token);
	List<Ad> findAllByUser(User user);
    List<Ad> findAllByUserAndStatus(User user, Status status);
    List<Ad> findAllByStatus(Status status);
    Slice<Ad> findAllByLocationAndStatus(Location location, Status status, Pageable pageable);
    Slice<Ad> findAllByCategoryAndLocationAndStatus(Category category, Location location, Status status, Pageable pageable);

    @Query("select a from Ad a where a.category = :category and a.status = :status and a.id != :id")
    Slice<Ad> findAllByCategoryAndStatusAndNotId(@Param("category") Category category, @Param("status") Status status, @Param("id") Long id, Pageable pageable);

    Long countByUser(User user);

    @Override
    void delete(Ad ad);
}
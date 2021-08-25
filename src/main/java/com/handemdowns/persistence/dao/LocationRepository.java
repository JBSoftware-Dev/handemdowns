package com.handemdowns.persistence.dao;

import com.handemdowns.persistence.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface LocationRepository extends JpaRepository<Location, String> {
	Location findByCode(String code);
    Location findByArea(String area);

	@Query("select a from Location a where a.city = :name or a.area = :name")
	Location findByName(@Param("name") String name);

    List<Location> findByCountry(String country);

	@Query("select a from Location a where a.city = :#{#location.city} and a.area != null")
	List<Location> findAreasByLocation(@Param("location") Location location);

	@Query("select distinct a.provState from Location a where a.country = :country order by a.provState")
	List<String> findProvStatesByCountry(@Param("country") String country);

	@Query("select distinct a.city from Location a where a.provState = :provState order by a.city")
	List<String> findCitiesByProvState(@Param("provState") String provState);

	@Query("select distinct a.area from Location a where a.city = :city order by a.area")
	List<String> findAreasByCity(@Param("city") String city);
}
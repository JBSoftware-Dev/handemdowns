package com.handemdowns.persistence.service;

import com.handemdowns.common.LocationTree;
import com.handemdowns.persistence.model.Location;

import java.util.List;

public interface ILocationService {
	List<Location> findAll();
	Location findByCode(String code);
    Location findByArea(String area);
	Location findByName(String name);
	List<Location> findByCountry(String country);
	List<Location> findAreasByLocation(Location location);

	List<String> findProvStatesByCountry(String country);
	List<String> findCitiesByProvState(String provState);
	List<String> findAreasByCity(String city);
	LocationTree buildLocationTree(String country);

	Location findNearestByCoordinates(double lat, double lon);
}
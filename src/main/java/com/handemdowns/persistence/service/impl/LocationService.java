package com.handemdowns.persistence.service.impl;

import com.handemdowns.common.LocationTree;
import com.handemdowns.persistence.dao.LocationRepository;
import com.handemdowns.persistence.dao.MapPlotRepository;
import com.handemdowns.persistence.model.Location;
import com.handemdowns.persistence.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LocationService implements ILocationService {
	private LocationRepository repository;
	private MapPlotRepository mapPlotRepository;

	@Autowired
	public LocationService(LocationRepository repository, MapPlotRepository mapPlotRepository) {
		this.repository = repository;
		this.mapPlotRepository = mapPlotRepository;
	}

	//@Cacheable(CacheConfig.LOCATION_CACHE)
	@Override
	public List<Location> findAll() {
		return repository.findAll();
	}

	@Override
	public Location findByCode(String code) {
		return repository.findByCode(code);
	}

    @Override
    public Location findByArea(String area) {
        return repository.findByArea(area);
    }

	@Override
	public Location findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public List<Location> findAreasByLocation(Location location) {
		return repository.findAreasByLocation(location);
	}

	@Override
	public List<Location> findByCountry(String country) {
		return repository.findByCountry(country);
	}

	@Override
	public Location findNearestByCoordinates(double lat, double lon) {
		return mapPlotRepository.findClosestMapPlot(lat, lon) != null ? mapPlotRepository.findClosestMapPlot(lat, lon).getLocation() : null;
	}

	@Override
	public List<String> findProvStatesByCountry(String country) {
		return repository.findProvStatesByCountry(country);
	}

	@Override
	public List<String> findCitiesByProvState(String provState) {
		return repository.findCitiesByProvState(provState);
	}

	@Override
	public List<String> findAreasByCity(String city) {
		return repository.findAreasByCity(city);
	}

	@Override
	public LocationTree buildLocationTree(String country) {
		LocationTree tree = new LocationTree(country);
		findProvStatesByCountry(country).forEach(provStateName-> {
			LocationTree.ProvState provState = new LocationTree.ProvState(provStateName);
			findCitiesByProvState(provStateName).forEach(cityName-> {
				LocationTree.City city = new LocationTree.City(cityName);
				findAreasByCity(cityName).forEach(areaName-> {
					city.getAreas().add(new LocationTree.Area(areaName));
				});
				provState.getCities().add(city);
			});
			tree.getProvStates().add(provState);
		});
		return tree;
	}
}
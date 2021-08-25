package com.handemdowns.persistence.service.impl;

import com.handemdowns.persistence.dao.MapPlotRepository;
import com.handemdowns.persistence.model.MapPlot;
import com.handemdowns.persistence.service.IMapPlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MapPlotService implements IMapPlotService {
	private MapPlotRepository repository;

	@Autowired
	public MapPlotService(MapPlotRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<MapPlot> findAll() {
		return repository.findAll();
	}

	@Override
	public List<MapPlot> findClosestMapPlotsByRadius(double centerLatitude, double centerLongitude, int radius, int records) {
		return repository.findClosestMapPlotsByRadius(centerLatitude, centerLongitude, radius, records);
	}
}
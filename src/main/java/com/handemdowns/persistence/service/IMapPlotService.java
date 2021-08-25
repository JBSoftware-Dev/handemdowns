package com.handemdowns.persistence.service;

import com.handemdowns.persistence.model.MapPlot;

import java.util.List;

public interface IMapPlotService {
	List<MapPlot> findAll();
	List<MapPlot> findClosestMapPlotsByRadius(double centerLatitude, double centerLongitude, int radius, int records);
}
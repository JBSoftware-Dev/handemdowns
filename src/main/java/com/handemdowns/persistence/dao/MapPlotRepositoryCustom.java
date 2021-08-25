package com.handemdowns.persistence.dao;

import com.handemdowns.persistence.model.MapPlot;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface MapPlotRepositoryCustom {
	MapPlot findClosestMapPlot(double latitude, double longitude);
	List<MapPlot> findClosestMapPlotsByRadius(double centerLatitude, double centerLongitude, int radius, int records);
}
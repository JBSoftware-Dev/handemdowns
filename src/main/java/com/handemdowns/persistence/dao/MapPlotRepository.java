package com.handemdowns.persistence.dao;

import com.handemdowns.persistence.model.MapPlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface MapPlotRepository extends JpaRepository<MapPlot, String>, MapPlotRepositoryCustom {
}
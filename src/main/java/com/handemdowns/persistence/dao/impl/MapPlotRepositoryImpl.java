package com.handemdowns.persistence.dao.impl;

import com.handemdowns.persistence.dao.MapPlotRepositoryCustom;
import com.handemdowns.persistence.model.MapPlot;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.search.annotations.Spatial;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.hibernate.search.spatial.DistanceSortField;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class MapPlotRepositoryImpl implements MapPlotRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MapPlot findClosestMapPlot(double latitude, double longitude) {
		int radius = 100;
		List<MapPlot> plots = findClosestMapPlotsByRadius(latitude, longitude, radius, 1);
		while (plots.size() == 0) {
			plots = findClosestMapPlotsByRadius(latitude, longitude, radius += 300, 1);
			if (radius > 5000) {
				return null;
			}
		}
		return plots.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MapPlot> findClosestMapPlotsByRadius(double centerLatitude, double centerLongitude, int radius, int records) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder builder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(MapPlot.class).get();
		Query luceneQuery = builder
				.spatial()
				.within(radius, Unit.KM)
				.ofLatitude(centerLatitude)
				.andLongitude(centerLongitude)
				.createQuery();
		FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, MapPlot.class);

		return jpaQuery
				.setSort(new Sort(new DistanceSortField(centerLatitude, centerLongitude, Spatial.COORDINATES_DEFAULT_FIELD)))
				.setMaxResults(records)
				.getResultList();
	}
}
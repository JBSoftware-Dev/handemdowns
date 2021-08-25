package com.handemdowns.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Latitude;
import org.hibernate.search.annotations.Longitude;
import org.hibernate.search.annotations.Spatial;

import javax.persistence.*;

@Entity
@Indexed
@Spatial
@Table(name = "map_plot", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"postalCode", "latitude", "longitude", "location"})
@ToString(exclude = {"location"})
@Builder(builderMethodName = "objectBuilder")
@JsonIgnoreProperties("location")
public class MapPlot {
	@Id
	@Column(unique = true, name = "postal_code")
	private String postalCode;

	@Latitude
	@Column(name = "latitude", nullable = false)
	private Double latitude;

	@Longitude
	@Column(name = "longitude", nullable = false)
	private Double longitude;

	@ManyToOne(optional = false)
	@JoinColumn(name = "location_code", nullable = false)
	private Location location;

	public static MapPlotBuilder builder(String postalCode, Double latitude, Double longitude, Location location) {
		return objectBuilder()
				.postalCode(postalCode)
				.latitude(latitude)
				.longitude(longitude)
				.location(location);
	}
}
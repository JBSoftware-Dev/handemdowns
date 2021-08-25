package com.handemdowns.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "location", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"code", "country", "provState", "city", "area"})
@ToString(exclude = "mapPlots")
@Builder(builderMethodName = "objectBuilder")
@JsonIgnoreProperties("mapPlots")
public class Location {
	@Id
	@Column(unique = true, name = "code", nullable = false)
    private String code;
	
	@Column(name = "country", nullable = false)
    private String country;
	
	@Column(name = "prov_state", nullable = false)
    private String provState;
	
	@Column(name = "city", nullable = false)
    private String city;
	
	@Column(name = "area")
    private String area;

	@OneToMany(mappedBy = "location")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<MapPlot> mapPlots;

	public String getDisplayName() {
		 return area != null ? area : city;
	}

	public static LocationBuilder builder(String code, String country, String provState, String city, String area) {
		return objectBuilder()
				.code(code)
				.country(country)
				.provState(provState)
				.city(city)
				.area(area);
	}
}
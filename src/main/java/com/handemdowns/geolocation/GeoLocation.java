package com.handemdowns.geolocation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@JsonDeserialize(using = GeoLocationDeserializer.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GeoLocation {
	private String city;
	private String postalCode;
	private double latitude;
	private double longitude;
}
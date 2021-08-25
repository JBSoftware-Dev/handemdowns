package com.handemdowns.service;

import com.handemdowns.geolocation.GeoLocation;

public interface IGeoLocationService {
    GeoLocation findGeoLocationByPostalCode(String postalCode);
    GeoLocation findGeoLocationByCoordinates(double lat, double lon);
}
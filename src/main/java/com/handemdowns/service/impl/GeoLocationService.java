package com.handemdowns.service.impl;

import com.handemdowns.geolocation.GeoLocation;
import com.handemdowns.service.IGeoLocationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeoLocationService implements IGeoLocationService {
    public static final String MAPS_API_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    @Value("${api.google.maps.key}")
    private String mapsKey;

    @Override
    public GeoLocation findGeoLocationByPostalCode(String postalCode) {
        return getForObject(UriComponentsBuilder.fromUriString(MAPS_API_BASE_URL).query("address={value}").buildAndExpand(postalCode));
    }

    @Override
    public GeoLocation findGeoLocationByCoordinates(double lat, double lon) {
        return getForObject(UriComponentsBuilder.fromUriString(MAPS_API_BASE_URL).query("latlng={value}").buildAndExpand(lat + "," + lon));
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

    private GeoLocation getForObject(UriComponents uriComponents) {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        return restTemplate.getForObject(uriComponents.toUri(), GeoLocation.class);
    }
}
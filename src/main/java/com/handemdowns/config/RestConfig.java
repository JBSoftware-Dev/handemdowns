package com.handemdowns.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.handemdowns.geolocation.GeoLocation;
import com.handemdowns.geolocation.GeoLocationDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
    @Bean
    public RestOperations restOperations() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, mappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(myObjectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper myObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(GeoLocation.class, new GeoLocationDeserializer());
        mapper.registerModule(module);
        return mapper;
    }
}
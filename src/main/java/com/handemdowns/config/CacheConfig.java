package com.handemdowns.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    public static final String CATEGORY_CACHE = "category";
    public static final String LOCATION_CACHE = "location";

    @Value("${cache.category.maximum.size}")
    private int categoryMaxSize;

    @Value("${cache.location.maximum.size}")
    private int locationMaxSize;

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Arrays.asList(
                buildCategoryCache(),
                buildLocationCache()
        ));
        return simpleCacheManager;
    }

    private GuavaCache buildCategoryCache() {
        return new GuavaCache(CATEGORY_CACHE, CacheBuilder
                .newBuilder()
                .maximumSize(categoryMaxSize)
                .expireAfterAccess(1, TimeUnit.DAYS)
                .build(),
                true);
    }

    private GuavaCache buildLocationCache() {
        return new GuavaCache(LOCATION_CACHE, CacheBuilder
                .newBuilder()
                .maximumSize(locationMaxSize)
                .expireAfterAccess(1, TimeUnit.DAYS)
                .build(),
                true);
    }
}
package com.handemdowns.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="cache")
@Data
public class CacheProperties {
    @Getter
    @Setter
    private Category category;

    @Getter
    @Setter
    private Location location;

    private static class Category {
        @Getter
        @Setter
        private Maximum maximum;
    }

    private static class Location {
        @Getter
        @Setter
        private Maximum maximum;
    }

    private static class Maximum {
        @Getter
        @Setter
        private int size;
    }
}
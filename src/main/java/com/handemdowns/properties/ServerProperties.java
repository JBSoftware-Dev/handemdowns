package com.handemdowns.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="server")
@Data
public class ServerProperties {
    @Getter
    @Setter
    private Application application;

    @Getter
    @Setter
    private Datasource datasource;

    private static class Application {
        @Getter
        @Setter
        private String url;
    }

    private static class Datasource {
        @Getter
        @Setter
        private String initialize;
    }
}
package com.handemdowns.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="cloud")
@Data
public class CloudProperties {
    @Getter
    @Setter
    private Aws aws;

    private static class Aws {
        @Getter
        @Setter
        private Credentials credentials;

        @Getter
        @Setter
        private Region region;

        @Getter
        @Setter
        private S3 s3;

        @Getter
        @Setter
        private Stack stack;
    }

    private static class Credentials {
        @Getter
        @Setter
        private String accessKey;

        @Getter
        @Setter
        private String secretKey;

        @Getter
        @Setter
        private Boolean instanceProfile;
    }

    private class Region {
        @Getter
        @Setter
        private Boolean auto;

        @Getter
        @Setter
        private String _static;
    }

    private static class S3 {
        @Getter
        @Setter
        private String bucket;
    }

    private static class Stack {
        @Getter
        @Setter
        private Boolean auto;
    }
}
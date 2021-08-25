package com.handemdowns.service.impl;

import com.handemdowns.service.IApplicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService implements IApplicationService {
    @Value("${server.application.url}")
    private String serverUrl;

    public String getServerUrl() {
        return serverUrl;
    }

    public boolean isDebug() {
        return serverUrl.contains("localhost");
    }

    public String getS3AdImageDirectory() {
        if (isDebug()) {
            return "dev-images/";
        }
        return "data-images/";
    }

    public String getAdImageUrl() {
        return "https://s3.amazonaws.com/handemdowns/" + getS3AdImageDirectory();
    }
}
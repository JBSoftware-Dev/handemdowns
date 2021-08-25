package com.handemdowns.service;

public interface IApplicationService {
    String getServerUrl();
    boolean isDebug();
    String getS3AdImageDirectory();
    String getAdImageUrl();
}
package com.handemdowns.service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface IStorageService {
    List<S3ObjectSummary> list();
    PutObjectResult upload(MultipartFile multipartFile, String uploadKey) throws IOException;
	PutObjectResult upload(BufferedImage bufferedImage, String format, String contentType, String uploadKey) throws IOException;
    ResponseEntity<byte[]> download(String key) throws IOException;
    void delete(String key);
}
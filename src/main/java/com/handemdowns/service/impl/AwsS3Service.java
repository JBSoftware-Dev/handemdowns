package com.handemdowns.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.handemdowns.service.IStorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@Service
public class AwsS3Service implements IStorageService {
    private static final String BUCKET = "handemdowns";

    private AmazonS3Client amazonS3Client;

	@Autowired
	public AwsS3Service(AmazonS3Client amazonS3Client) {
		this.amazonS3Client = amazonS3Client;
	}

	@Override
    public List<S3ObjectSummary> list() {
        ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(BUCKET));
        return objectListing.getObjectSummaries();
    }

    @Override
    public PutObjectResult upload(MultipartFile multipartFile, String uploadKey) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        byte[] byteArr = multipartFile.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);

        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, uploadKey, inputStream, objectMetadata);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
        IOUtils.closeQuietly(inputStream);

        return putObjectResult;
    }

	@Override
	public PutObjectResult upload(BufferedImage bufferedImage, String format, String contentType, String uploadKey) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, format, os);
		byte[] byteArr = os.toByteArray();
		InputStream inputStream = new ByteArrayInputStream(byteArr);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(byteArr.length);

		PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, uploadKey, inputStream, objectMetadata);
		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
		IOUtils.closeQuietly(inputStream);

		return putObjectResult;
	}

    @Override
    public ResponseEntity<byte[]> download(String key) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(BUCKET, key);
        S3Object s3Object = amazonS3Client.getObject(getObjectRequest);
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    @Override
    public void delete(String key) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(BUCKET, key));
    }
}
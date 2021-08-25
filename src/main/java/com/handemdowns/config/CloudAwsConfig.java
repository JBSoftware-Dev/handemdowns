package com.handemdowns.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudAwsConfig {
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Bean
    public AWSCredentials AWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
        return new AmazonS3Client(awsCredentials);
    }

    @Bean
    public AmazonSimpleEmailServiceClient amazonSesClient(AWSCredentials awsCredentials) {
        return new AmazonSimpleEmailServiceClient(awsCredentials);
    }
}
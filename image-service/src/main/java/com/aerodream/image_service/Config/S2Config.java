package com.aerodream.image_service.Config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S2Config {

    @Value("AWS.ACCESS.KEY")
    private String accessKey;

    @Value("AWS.SECRET.KEY")
    private String secretKey;

    @Value("AWS.REGION")
    private String region;

    @Value("AWS.BUCKET.NAME")
    private String bucketName;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    @Bean
    public String bucketName() {
        return bucketName;
    }
}

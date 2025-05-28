package com.example.demo.service;

import com.example.demo.IntegrationTestSupport;
import com.example.demo.dto.BinaryContent;
import com.example.demo.dto.request.BinaryContentRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

class BinaryContentStorageServiceTest extends IntegrationTestSupport {

    @Autowired
    private BinaryContentStorageService binaryContentStorageService;

    @Autowired
    private S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.base-url}")
    private String baseUrl;

    @Disabled
    @DisplayName("바이너리 이미지를 입력하면, imageUrl을 반환합니다.")
    @Test
    void create() {
        // given
        String fileName = "fileName";
        byte[] bytes = "hello".getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        BinaryContentRequest binaryContentRequest = new BinaryContentRequest(fileName, bytes.length, inputStream);

        // when
        BinaryContent binaryContent = binaryContentStorageService.create(binaryContentRequest);

        // then
        Assertions.assertThat(getObjectFromS3(binaryContent)).isEqualTo(bytes);
    }

    private byte[] getObjectFromS3(BinaryContent binaryContent) {
        String key = binaryContent.imageUrl().substring(baseUrl.length());

        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getRequest);

        return extracted(s3Object);
    }

    private byte[] extracted(ResponseInputStream<GetObjectResponse> s3Object) {
        try {
            return s3Object.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
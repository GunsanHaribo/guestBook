package com.example.demo.service.impl;

import com.example.demo.dto.BinaryContent;
import com.example.demo.dto.BinaryContentRequest;
import com.example.demo.service.BinaryContentStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BinaryContentStorageServiceImpl implements BinaryContentStorageService {

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.base-url}")
    private String baseUrl;

    private final S3Client s3Client;

    @Override
    public BinaryContent create(BinaryContentRequest binaryContentRequest) {
        // 키생성
        String key = binaryContentRequest.name() + UUID.randomUUID();

        // S3 업로드하기
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/jpeg")
                .build();

        // 이게왜 빨간줄뜨는데 이유가뭐지
        RequestBody requestBody = RequestBody.fromInputStream(binaryContentRequest.inputStream(), binaryContentRequest.size());
        s3Client.putObject(putObjectRequest, requestBody);

        String url = baseUrl + key;
        return new BinaryContent(url);
    }

}

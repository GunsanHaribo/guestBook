package com.example.demo.service;

import com.example.demo.IntegrationTestSupport;
import com.example.demo.dto.BinaryContentRequest;
import com.example.demo.dto.GuestBookRequest;
import com.example.demo.dto.GuestBookResult;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

class GuestBookServiceImplTest extends IntegrationTestSupport {

    @Autowired
    private GuestBookService guestBookService;

    @Value("${aws.base-url}")
    private String baseUrl;

    @Value("${aws.bucketName}")
    private String bucketName;


    @Transactional
    @DisplayName("이름, 이미지를 입려하면, 방명록을 반환합니다.")
    @Test
    void create() {
        // given
        String guestName = UUID.randomUUID().toString();
        String title = UUID.randomUUID().toString();
        String content = UUID.randomUUID().toString();
        String imageName = UUID.randomUUID().toString();
        byte[] bytes = UUID.randomUUID().toString().getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);

        GuestBookRequest guestBookRequest = new GuestBookRequest(guestName, title, content);
        BinaryContentRequest binaryContentRequest = new BinaryContentRequest(imageName, bytes.length, inputStream);

        // when
        GuestBookResult guestBookResult = guestBookService.create(guestBookRequest, binaryContentRequest);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(guestBookResult)
                    .extracting(GuestBookResult::name, GuestBookResult::title)
                    .containsExactlyInAnyOrder(guestName, title);
            softly.assertThat(guestBookResult.createdAt()).isNotNull();
            softly.assertThat(guestBookResult.imageUrl().startsWith(baseUrl + imageName)).isTrue();
        });
    }

}
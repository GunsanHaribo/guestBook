package com.example.demo.service;

import com.example.demo.IntegrationTestSupport;
import com.example.demo.dto.BinaryContentRequest;
import com.example.demo.dto.GuestBookRequest;
import com.example.demo.dto.GuestBookResult;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

class GuestBookServiceImplTest extends IntegrationTestSupport {

    @Autowired
    private GuestBookService guestBookService;

    @DisplayName("이름, 이미지를 입려하면, 방명록을 반환합니다.")
    @Test
    void create() {
        // given
        String guestName = UUID.randomUUID().toString();
        String title = UUID.randomUUID().toString();
        String content = UUID.randomUUID().toString();
        String imageName = UUID.randomUUID().toString();
        String imageFile = UUID.randomUUID().toString();
        InputStream inputStream = new ByteArrayInputStream(imageFile.getBytes());

        GuestBookRequest guestBookRequest = new GuestBookRequest(guestName, title, content);
        BinaryContentRequest binaryContentRequest = new BinaryContentRequest(imageName, 0, inputStream);

        // when
        GuestBookResult guestBookResult = guestBookService.create(guestBookRequest, binaryContentRequest);

        // then
        // 이게 도중에 S3에 갔다와야되는데... 이걸 흐음... 이러면 속도가 너무 느려질텐데,, 목이나 다른 방법 사용해야합니다.
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(guestBookResult)
                    .extracting(GuestBookResult::name, GuestBookResult::title, GuestBookResult::imageUrl)
                    .containsExactlyInAnyOrder(guestName, title);
            softly.assertThat(guestBookResult.createdAt()).isNotNull();
        });
    }

//    {
//        "id": "방명록ID",
//            "name": "작성자 이름",
//            "title": "방명록 제목",
//            "content": "방명록 내용",
//            "imageUrl": "이미지URL 또는 null",
//            "createdAt": "생성일시"
//    }

}
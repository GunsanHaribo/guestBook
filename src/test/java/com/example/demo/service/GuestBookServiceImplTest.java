package com.example.demo.service;

import com.example.demo.IntegrationTestSupport;
import com.example.demo.dto.BinaryContent;
import com.example.demo.dto.request.BinaryContentRequest;
import com.example.demo.dto.request.GuestBookCreateRequest;
import com.example.demo.dto.request.GuestBookPageRequest;
import com.example.demo.dto.result.GuestBookResult;
import com.example.demo.dto.result.PageResult;
import com.example.demo.entity.GuestBook;
import com.example.demo.repository.GuestBookRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

class GuestBookServiceImplTest extends IntegrationTestSupport {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Autowired
    private GuestBookService sut;

    @Value("${aws.base-url}")
    private String baseUrl;

    @MockitoBean
    private BinaryContentStorageService binaryContentStorageService;

    @Transactional
    @DisplayName("이름, 이미지를 입력하면, 방명록을 반환합니다.")
    @Test
    void create() {
        // given
        String guestName = UUID.randomUUID().toString();
        String title = UUID.randomUUID().toString();
        String content = UUID.randomUUID().toString();
        String imageName = UUID.randomUUID().toString();
        byte[] bytes = UUID.randomUUID().toString().getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);

        GuestBookCreateRequest guestBookCreateRequest = new GuestBookCreateRequest(guestName, title, content);
        BinaryContentRequest binaryContentRequest = new BinaryContentRequest(imageName, bytes.length, inputStream);
        UUID random = UUID.randomUUID();
        String key = binaryContentRequest.name() + random;
        String url = baseUrl + key;
        BinaryContent binaryContent = new BinaryContent(url);
        BDDMockito.given(binaryContentStorageService.create(any()))
                .willReturn(binaryContent);

        // when
        GuestBookResult guestBookResult = sut.create(guestBookCreateRequest, binaryContentRequest);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(guestBookResult)
                    .extracting(GuestBookResult::name, GuestBookResult::title)
                    .containsExactlyInAnyOrder(guestName, title);
            softly.assertThat(guestBookResult.createdAt()).isNotNull();
            softly.assertThat(guestBookResult.imageUrl()).isEqualTo(url);
        });
    }

    @Transactional
    @DisplayName("방명록을 조회하면, 요구한 양만큼 반환한다")
    @Test
    void getAllIn() {
        // given
        GuestBook guestBook = guestBookRepository.save(new GuestBook("", "", "", ""));
        GuestBook guestBook2 = guestBookRepository.save(new GuestBook("", "", "", ""));
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        GuestBookPageRequest guestBookPageRequest = new GuestBookPageRequest(0, 1, sort);

        // when
        PageResult<GuestBookResult> guestBooks = sut.getAllIn(guestBookPageRequest);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(guestBooks.contents())
                    .hasSize(1);
            softly.assertThat(guestBooks.contents())
                    .extracting(GuestBookResult::id)
                    .containsExactlyInAnyOrder(guestBook2.getId());
        });
    }

}
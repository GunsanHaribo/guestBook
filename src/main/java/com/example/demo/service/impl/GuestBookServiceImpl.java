package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.dto.request.BinaryContentRequest;
import com.example.demo.dto.request.GuestBookCreateRequest;
import com.example.demo.dto.request.GuestBookPageRequest;
import com.example.demo.dto.result.GuestBookResult;
import com.example.demo.dto.result.PageResult;
import com.example.demo.entity.GuestBook;
import com.example.demo.repository.GuestBookRepository;
import com.example.demo.service.BinaryContentStorageService;
import com.example.demo.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService {

    private final BinaryContentStorageService binaryContentStorageService;
    private final GuestBookRepository guestBookRepository;

    @Override
    public GuestBookResult create(GuestBookCreateRequest guestBookCreateRequest, BinaryContentRequest binaryContentRequest) {
        BinaryContent binaryContent = binaryContentStorageService.create(binaryContentRequest);

        GuestBook guestBook = new GuestBook(guestBookCreateRequest.name(), guestBookCreateRequest.title(), guestBookCreateRequest.content(), binaryContent.imageUrl());
        GuestBook savedGuestBook = guestBookRepository.save(guestBook);

        return GuestBookResult.from(savedGuestBook);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResult<GuestBookResult> getAllIn(GuestBookPageRequest guestBookPageRequest) {
        Pageable pageable = PageRequest.of(guestBookPageRequest.pageNumber(), guestBookPageRequest.pageSize(), guestBookPageRequest.sort());
        Page<GuestBook> guestBooks = guestBookRepository.findAll(pageable);

        return PageResult.from(guestBooks, GuestBookResult::from);
    }

    @Transactional(readOnly = true)
    @Override
    public GuestBookResult getById(Long id) {
        GuestBook guestBook = guestBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 객체가 없습니다."));

        return GuestBookResult.from(guestBook);
    }

}

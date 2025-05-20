package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entity.GuestBook;
import com.example.demo.repository.GuestBookRepository;
import com.example.demo.service.BinaryContentStorageService;
import com.example.demo.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService {

    private final BinaryContentStorageService binaryContentStorageService;
    private final GuestBookRepository guestBookRepository;

    @Override
    public GuestBookResult create(GuestBookRequest guestBookRequest, BinaryContentRequest binaryContentRequest) {
        // 일단 S3에 저장을 합니다.
        BinaryContent binaryContent = binaryContentStorageService.create(binaryContentRequest);

        //        new GuestBook();
//        guestBookRepository.save();

        //

        return null;
    }

    @Override
    public PageResult<GuestBookResult> getAllIn() {
        return null;
    }

    @Override
    public GuestBookResult getById(Long id) {
        return null;
    }

}

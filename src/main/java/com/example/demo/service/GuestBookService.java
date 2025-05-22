package com.example.demo.service;

import com.example.demo.dto.request.BinaryContentRequest;
import com.example.demo.dto.request.GuestBookCreateRequest;
import com.example.demo.dto.request.GuestBookPageRequest;
import com.example.demo.dto.result.GuestBookResult;
import com.example.demo.dto.result.PageResult;

public interface GuestBookService {

    GuestBookResult create(GuestBookCreateRequest guestBookCreateRequest, BinaryContentRequest binaryContentRequest);

    PageResult<GuestBookResult> getAllIn(GuestBookPageRequest guestBookPageRequest);

    GuestBookResult getById(Long id);

}

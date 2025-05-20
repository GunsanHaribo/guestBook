package com.example.demo.service;

import com.example.demo.dto.BinaryContentRequest;
import com.example.demo.dto.GuestBookRequest;
import com.example.demo.dto.GuestBookResult;
import com.example.demo.dto.PageResult;

public interface GuestBookService {

    GuestBookResult create(GuestBookRequest guestBookRequest, BinaryContentRequest binaryContentRequest);

    PageResult<GuestBookResult> getAllIn();

    GuestBookResult getById(Long id);

}

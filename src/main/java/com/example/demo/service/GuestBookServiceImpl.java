package com.example.demo.service;

import com.example.demo.dto.BinaryContentRequest;
import com.example.demo.dto.GuestBookRequest;
import com.example.demo.dto.GuestBookResult;
import com.example.demo.dto.PageResult;
import org.springframework.stereotype.Service;

@Service
public class GuestBookServiceImpl implements GuestBookService {

    @Override
    public GuestBookResult create(GuestBookRequest guestBookRequest, BinaryContentRequest binaryContentRequest) {
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

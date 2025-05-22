package com.example.demo.controller;

import com.example.demo.dto.request.BinaryContentRequest;
import com.example.demo.dto.request.GuestBookCreateRequest;
import com.example.demo.dto.request.GuestBookPageRequest;
import com.example.demo.dto.result.GuestBookResult;
import com.example.demo.dto.result.PageResult;
import com.example.demo.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/guestbooks")
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService guestBookService;

    @PostMapping
    public GuestBookResult create(@RequestPart GuestBookCreateRequest guestBookCreateRequest, @RequestPart(required = false) MultipartFile image) {
        BinaryContentRequest binaryContentRequest = convertToBinaryRequest(image);
        return guestBookService.create(guestBookCreateRequest, binaryContentRequest);
    }

    @GetMapping
    public PageResult<GuestBookResult> getAllIn(
            @PageableDefault(
                    size = 5,
                    page = 0,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        GuestBookPageRequest guestBookPageRequest = GuestBookPageRequest.from(pageable);
        return guestBookService.getAllIn(guestBookPageRequest);
    }

    @GetMapping("/{id}")
    public GuestBookResult getById(@PathVariable(name = "id") Long id) {
        return guestBookService.getById(id);
    }

    private BinaryContentRequest convertToBinaryRequest(MultipartFile multipartFile) {
        if (multipartFile == null) {
            return null;
        }

        try {
            return new BinaryContentRequest(multipartFile.getName(), multipartFile.getSize(), multipartFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("파일이 없습니다.");
        }
    }

}

package com.example.demo.controller;

import com.example.demo.dto.BinaryContentRequest;
import com.example.demo.dto.GuestBookRequest;
import com.example.demo.dto.GuestBookResult;
import com.example.demo.dto.PageResult;
import com.example.demo.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/guestbooks")
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService guestBookService;

    @PostMapping
    public GuestBookResult create(@RequestPart GuestBookRequest guestBookRequest, @RequestPart(required = false) MultipartFile image) {
        BinaryContentRequest binaryContentRequest = convertToBinaryRequest(image);
        return guestBookService.create(guestBookRequest, binaryContentRequest);
    }

    @GetMapping
    public PageResult<GuestBookResult> getAllIn() {
        return guestBookService.getAllIn();
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

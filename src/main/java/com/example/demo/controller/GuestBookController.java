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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/guestbooks")
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService guestBookService;

    @PostMapping
    public ResponseEntity<GuestBookResult> create(

            @RequestPart(name = "name") String name,
            @RequestPart(name = "title") String title,
            @RequestPart(name = "content") String content,
            @RequestPart(name = "image", required = false) MultipartFile image
    ) {
        // null일때 손봐야된다.
        BinaryContentRequest binaryContentRequest = convertToBinaryRequest(image);
        GuestBookCreateRequest guestBookCreateRequest = new GuestBookCreateRequest(name, title, content);
        GuestBookResult guestBookResult = guestBookService.create(guestBookCreateRequest, binaryContentRequest);

        return ResponseEntity.ok(guestBookResult);
    }

    @GetMapping
    public ResponseEntity<PageResult<GuestBookResult>> getAllIn(
            @PageableDefault(
                    size = 5,
                    page = 0,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        GuestBookPageRequest guestBookPageRequest = GuestBookPageRequest.from(pageable);
        PageResult<GuestBookResult> guestBookResultPageResult = guestBookService.getAllIn(guestBookPageRequest);

        return ResponseEntity.ok(guestBookResultPageResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestBookResult> getById(@PathVariable(name = "id") Long id) {
        GuestBookResult guestBookResult = guestBookService.getById(id);

        return ResponseEntity.ok(guestBookResult);
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

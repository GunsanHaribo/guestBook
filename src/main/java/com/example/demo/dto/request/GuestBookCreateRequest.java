package com.example.demo.dto.request;

public record GuestBookCreateRequest(
        String name,
        String title,
        String content
) {
}

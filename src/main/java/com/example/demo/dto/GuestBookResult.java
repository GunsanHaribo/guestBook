package com.example.demo.dto;

import java.time.Instant;

public record GuestBookResult(
        Long id,
        String name,
        String title,
        String content,
        String imageUrl,
        Instant createdAt
) {
}

package com.example.demo.dto;

public record GuestBookRequest(
        String name,
        String title,
        String content
) {
}

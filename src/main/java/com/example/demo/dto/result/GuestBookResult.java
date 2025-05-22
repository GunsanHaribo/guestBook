package com.example.demo.dto.result;

import com.example.demo.entity.GuestBook;

import java.time.Instant;

public record GuestBookResult(
        Long id,
        String name,
        String title,
        String content,
        String imageUrl,
        Instant createdAt
) {

    public static GuestBookResult from(GuestBook guestBook) {
        return new GuestBookResult(
                guestBook.getId(),
                guestBook.getName(),
                guestBook.getTitle(),
                guestBook.getContent(),
                guestBook.getImageUrl(),
                guestBook.getCreatedAt()
        );
    }

}

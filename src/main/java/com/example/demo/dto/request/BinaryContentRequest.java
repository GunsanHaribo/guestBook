package com.example.demo.dto.request;

import java.io.InputStream;

public record BinaryContentRequest(
        String name,
        long size,
        InputStream inputStream
) {
}

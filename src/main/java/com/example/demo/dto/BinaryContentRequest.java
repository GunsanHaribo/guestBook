package com.example.demo.dto;

import java.io.InputStream;

public record BinaryContentRequest(
        String name,
        long size,
        InputStream inputStream
) {
}

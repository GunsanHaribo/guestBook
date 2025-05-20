package com.example.demo.dto;

import java.util.List;

public record PageResult<T>(
        List<T> content,
        int totalPages,
        int totalElements,
        int size,
        int number
) {
}

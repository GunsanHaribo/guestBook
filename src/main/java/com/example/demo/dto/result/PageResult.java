package com.example.demo.dto.result;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PageResult<T>(
        List<T> contents,
        int totalPages,
        int totalElements,
        int size,
        int number
) {

    public static <T, U> PageResult<U> from(Page<T> page, Function<T, U> mapper) {
        List<U> contents = page.getContent()
                .stream()
                .map(mapper)
                .toList();

        return new PageResult<>(
                contents,
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getSize(),
                page.getNumber()
        );
    }

}

package com.example.demo.dto.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record GuestBookPageRequest(
        int pageNumber,
        int pageSize,
        Sort sort
) {

    public static GuestBookPageRequest from(Pageable pageable) {
        return new GuestBookPageRequest(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort()
        );
    }

}

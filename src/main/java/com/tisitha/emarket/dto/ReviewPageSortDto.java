package com.tisitha.emarket.dto;

import java.util.List;

public record ReviewPageSortDto(
        List<ReviewResponseDto> reviewResponseDtoList ,
        long totalElement,
        int pageCount,
        boolean isLast) {}

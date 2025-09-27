package com.tisitha.emarket.dto;

import java.util.List;

public record OrderPageSortDto (
        List<OrderResponseDto> orderResponseDtoList,
        long totalElement,
        int pageCount,
        boolean isLast
){}
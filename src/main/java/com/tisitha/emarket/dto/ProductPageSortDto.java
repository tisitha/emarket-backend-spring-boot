package com.tisitha.emarket.dto;

import java.util.List;

public record ProductPageSortDto(
    List<ProductResponseDto> productResponseDtoList ,
    long totalElement,
    int pageCount,
    boolean isLast
){}

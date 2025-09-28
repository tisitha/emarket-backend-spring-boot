package com.tisitha.emarket.dto;

import java.util.List;

public record AccountPageSortDto(
        List<AccountResponseDto> accountResponseDtoList,
        long totalElement,
        int pageCount,
        boolean isLast
) {}

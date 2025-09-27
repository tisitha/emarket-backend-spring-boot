package com.tisitha.emarket.dto;

import java.util.List;

public record QuestionPageSortDto (
    List<QuestionResponseDto> questionResponseDtoList ,
    long totalElement,
    int pageCount,
    boolean isLast) {}


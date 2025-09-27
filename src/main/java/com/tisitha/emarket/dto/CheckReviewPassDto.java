package com.tisitha.emarket.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CheckReviewPassDto {

    private UUID productId;

    private UUID userId;
}

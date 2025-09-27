package com.tisitha.emarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {

    private String body;

    private Integer rate;

    private UUID productId;

}

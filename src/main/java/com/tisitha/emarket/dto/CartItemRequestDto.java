package com.tisitha.emarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDto {

    private Integer quantity;

    private UUID productId;

}

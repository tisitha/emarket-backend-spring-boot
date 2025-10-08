package com.tisitha.emarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDto {

    private UUID id;

    private UserResponseDto user;

    private Integer quantity;

    private ProductResponseDto product;

}

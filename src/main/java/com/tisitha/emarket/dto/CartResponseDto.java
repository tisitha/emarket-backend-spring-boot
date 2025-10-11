package com.tisitha.emarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {

    private List<CartItemResponseDto> cartItems;

    private Double subTotalCost;

    private Double deliveryCost;

    private Double totalCost;
}

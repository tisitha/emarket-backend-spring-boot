package com.tisitha.emarket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDto {

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull(message = "Product Id is required")
    private UUID productId;

}

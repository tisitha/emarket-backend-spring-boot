package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.Product;
import com.tisitha.emarket.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDto {

    private User user;

    private Integer quantity;

    private Product product;

}

package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.CartItemRequestDto;
import com.tisitha.emarket.dto.CartItemResponseDto;

import java.util.List;
import java.util.UUID;

public interface CartItemService {

    List<CartItemResponseDto> getCartByUser(UUID userId);

    CartItemResponseDto addCartItem(CartItemRequestDto cartItemRequestDto);

    CartItemResponseDto updateCartItem(UUID cartItemId,CartItemRequestDto cartItemRequestDto);

    void deleteCartItem(UUID cartItemId);

}

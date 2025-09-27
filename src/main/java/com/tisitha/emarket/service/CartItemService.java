package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.CartItemRequestDto;
import com.tisitha.emarket.dto.CartItemResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface CartItemService {

    List<CartItemResponseDto> getCartByUser(Authentication authentication);

    CartItemResponseDto addCartItem(CartItemRequestDto cartItemRequestDto,Authentication authentication);

    CartItemResponseDto updateCartItem(UUID cartItemId,CartItemRequestDto cartItemRequestDto,Authentication authentication);

    void deleteCartItem(UUID cartItemId,Authentication authentication);

}

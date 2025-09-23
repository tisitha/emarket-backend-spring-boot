package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.CartItemRequestDto;
import com.tisitha.emarket.dto.CartItemResponseDto;
import com.tisitha.emarket.model.CartItem;
import com.tisitha.emarket.repo.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartItemServiceImp implements CartItemService{

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImp(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItemResponseDto> getCartByUser(UUID userId) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        return cartItems.stream().map(this::mapCartItemToCartItemDto).toList();
    }

    @Override
    public CartItemResponseDto addCartItem(CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(cartItemRequestDto.getUser());
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        cartItem.setProduct(cartItemRequestDto.getProduct());
        CartItem newCartItem = cartItemRepository.save(cartItem);
        return mapCartItemToCartItemDto(newCartItem);
    }

    @Override
    public CartItemResponseDto updateCartItem(UUID cartItemId, CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()->new RuntimeException(""));
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        CartItem newCartItem = cartItemRepository.save(cartItem);
        if(newCartItem.getQuantity()==0){
            cartItemRepository.deleteById(cartItemId);
        }
        return mapCartItemToCartItemDto(newCartItem);
    }

    @Override
    public void deleteCartItem(UUID cartItemId) {
        cartItemRepository.findById(cartItemId).orElseThrow(()->new RuntimeException(""));
        cartItemRepository.deleteById(cartItemId);
    }

    private CartItemResponseDto mapCartItemToCartItemDto(CartItem cartItem){
        return new CartItemResponseDto(
                cartItem.getId(),
                cartItem.getUser(),
                cartItem.getQuantity(),
                cartItem.getProduct()
        );
    }

}

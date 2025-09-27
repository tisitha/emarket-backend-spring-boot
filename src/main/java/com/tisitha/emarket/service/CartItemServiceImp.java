package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.CartItemRequestDto;
import com.tisitha.emarket.dto.CartItemResponseDto;
import com.tisitha.emarket.model.CartItem;
import com.tisitha.emarket.model.Product;
import com.tisitha.emarket.model.User;
import com.tisitha.emarket.repo.CartItemRepository;
import com.tisitha.emarket.repo.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartItemServiceImp implements CartItemService{

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartItemServiceImp(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CartItemResponseDto> getCartByUser(Authentication authentication) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserEmail(authentication.getName());
        return cartItems.stream().map(this::mapCartItemToCartItemDto).toList();
    }

    @Override
    public CartItemResponseDto addCartItem(CartItemRequestDto cartItemRequestDto,Authentication authentication) {
        Product product = productRepository.findById(cartItemRequestDto.getProductId()).orElseThrow(()->new RuntimeException(""));
        if(cartItemRequestDto.getQuantity()==0 || cartItemRequestDto.getQuantity()> product.getQuantity()){
            throw new RuntimeException("");
        }
        CartItem cartItem = new CartItem();
        cartItem.setUser((User)authentication.getPrincipal());
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        cartItem.setProduct(product);
        CartItem newCartItem = cartItemRepository.save(cartItem);
        return mapCartItemToCartItemDto(newCartItem);
    }

    @Override
    public CartItemResponseDto updateCartItem(UUID cartItemId, CartItemRequestDto cartItemRequestDto,Authentication authentication) {
        Product product = productRepository.findById(cartItemRequestDto.getProductId()).orElseThrow(()->new RuntimeException(""));
        if(cartItemRequestDto.getQuantity()==0 || cartItemRequestDto.getQuantity()> product.getQuantity()){
            throw new RuntimeException("");
        }
        CartItem cartItem = cartItemRepository.findByIdAndUserEmail(cartItemId,authentication.getName()).orElseThrow(()->new RuntimeException(""));
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        CartItem newCartItem = cartItemRepository.save(cartItem);
        if(newCartItem.getQuantity()==0){
            cartItemRepository.deleteById(cartItemId);
        }
        return mapCartItemToCartItemDto(newCartItem);
    }

    @Override
    public void deleteCartItem(UUID cartItemId,Authentication authentication) {
        cartItemRepository.findByIdAndUserEmail(cartItemId,authentication.getName()).orElseThrow(()->new RuntimeException(""));
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

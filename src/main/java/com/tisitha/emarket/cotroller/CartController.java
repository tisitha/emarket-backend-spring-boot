package com.tisitha.emarket.cotroller;

import com.tisitha.emarket.dto.CartItemRequestDto;
import com.tisitha.emarket.dto.CartItemResponseDto;
import com.tisitha.emarket.dto.CartResponseDto;
import com.tisitha.emarket.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/cart")
public class CartController {

    private final CartItemService cartItemService;

    public CartController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<CartItemResponseDto> addToCart(@Valid @RequestBody CartItemRequestDto cartItemRequestDto, Authentication authentication){
        return new ResponseEntity<>(cartItemService.addCartItem(cartItemRequestDto,authentication), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(Authentication authentication){
        return new ResponseEntity<>(cartItemService.getCartByUser(authentication),HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}/{newQuantity}")
    public ResponseEntity<CartItemResponseDto> updateCart(@PathVariable UUID cartItemId,@PathVariable Integer newQuantity, Authentication authentication){
        return new ResponseEntity<>(cartItemService.updateCartItem(cartItemId,newQuantity,authentication),HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable UUID cartItemId, Authentication authentication){
        cartItemService.deleteCartItem(cartItemId,authentication);
        return ResponseEntity.ok().build();
    }

}

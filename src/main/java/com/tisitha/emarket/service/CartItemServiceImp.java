package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.CartItemRequestDto;
import com.tisitha.emarket.dto.CartItemResponseDto;
import com.tisitha.emarket.dto.CartResponseDto;
import com.tisitha.emarket.exception.CartItemNotFoundException;
import com.tisitha.emarket.exception.ProductNotFoundException;
import com.tisitha.emarket.exception.ProductOutOfStockException;
import com.tisitha.emarket.model.CartItem;
import com.tisitha.emarket.model.Product;
import com.tisitha.emarket.model.SiteConfigName;
import com.tisitha.emarket.model.User;
import com.tisitha.emarket.repo.CartItemRepository;
import com.tisitha.emarket.repo.ProductRepository;
import com.tisitha.emarket.repo.SiteConfigRepository;
import com.tisitha.emarket.util.ObjectConverter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartItemServiceImp implements CartItemService{

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final SiteConfigRepository siteConfigRepository;

    public CartItemServiceImp(CartItemRepository cartItemRepository, ProductRepository productRepository, SiteConfigRepository siteConfigRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.siteConfigRepository = siteConfigRepository;
    }

    @Override
    public CartResponseDto getCartByUser(Authentication authentication) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserEmail(authentication.getName());
        double totalSubCost = 0;
        double totalDeliveryCost = 0;
        for(CartItem cartItem:cartItems){
            totalSubCost+=cartItem.getProduct().getDeal()==0?cartItem.getProduct().getPrice():cartItem.getProduct().getDeal();;
            totalDeliveryCost+=cartItem.getProduct().isFreeDelivery()?0.0:Double.parseDouble(siteConfigRepository.findByName(SiteConfigName.DELIVERY_COST.name()).get().getValue());
        }
        return new CartResponseDto(
                cartItems.stream().map(ObjectConverter::mapCartItemToCartItemDto).toList(),
                totalSubCost,
                totalDeliveryCost,
                totalSubCost+totalDeliveryCost
                );
    }

    @Override
    public CartItemResponseDto addCartItem(CartItemRequestDto cartItemRequestDto,Authentication authentication) {
        Product product = productRepository.findById(cartItemRequestDto.getProductId()).orElseThrow(ProductNotFoundException::new);
        if(cartItemRequestDto.getQuantity()==0 || cartItemRequestDto.getQuantity()> product.getQuantity()){
            throw new ProductOutOfStockException();
        }
        CartItem cartItem = cartItemRepository.findByProductIdAndUserEmail(product.getId(),authentication.getName()).orElse(new CartItem());
        cartItem.setUser((User)authentication.getPrincipal());
        cartItem.setQuantity((cartItem.getQuantity()==null?0:cartItem.getQuantity())+cartItemRequestDto.getQuantity());
        if(cartItem.getQuantity()>product.getQuantity()){
            cartItem.setQuantity(product.getQuantity());
        }
        cartItem.setProduct(product);
        CartItem newCartItem = cartItemRepository.save(cartItem);
        return ObjectConverter.mapCartItemToCartItemDto(newCartItem);
    }

    @Override
    public CartItemResponseDto updateCartItem(UUID cartItemId, Integer newQuantity,Authentication authentication) {
        CartItem cartItem = cartItemRepository.findByIdAndUserEmail(cartItemId,authentication.getName()).orElseThrow(CartItemNotFoundException::new);
        Product product = cartItem.getProduct();
        if(product==null){
            cartItemRepository.deleteById(cartItemId);
            throw new ProductNotFoundException();
        }
        if(newQuantity==0 || newQuantity> product.getQuantity()){
            cartItemRepository.deleteById(cartItemId);
            throw new ProductOutOfStockException();
        }
        cartItem.setQuantity(newQuantity);
        CartItem newCartItem = cartItemRepository.save(cartItem);
        return ObjectConverter.mapCartItemToCartItemDto(newCartItem);
    }

    @Override
    public void deleteCartItem(UUID cartItemId,Authentication authentication) {
        cartItemRepository.findByIdAndUserEmail(cartItemId, authentication.getName()).orElseThrow(CartItemNotFoundException::new);
        cartItemRepository.deleteById(cartItemId);
    }

}

package com.tisitha.emarket.exception;

public class CartItemNotFoundException extends ResourceNotFoundException{

    public CartItemNotFoundException() {
        super("Cannot find cart item information");
    }
}

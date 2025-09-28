package com.tisitha.emarket.exception;

public class ProductNotFoundException extends ResourceNotFoundException{

    public ProductNotFoundException() {
        super("Cannot find product information");
    }

}

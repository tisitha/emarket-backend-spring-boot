package com.tisitha.emarket.exception;

public class OrderNotFoundException extends ResourceNotFoundException{

    public OrderNotFoundException() {
        super("Cannot find order information");
    }

}

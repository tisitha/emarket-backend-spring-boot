package com.tisitha.emarket.exception;

public class PaymentMethodNotFoundException extends ResourceNotFoundException{

    public PaymentMethodNotFoundException(){
        super("Cannot find payment method information");
    }
}

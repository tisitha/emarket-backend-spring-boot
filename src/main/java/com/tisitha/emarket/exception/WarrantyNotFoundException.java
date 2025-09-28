package com.tisitha.emarket.exception;

public class WarrantyNotFoundException extends ResourceNotFoundException{
    public WarrantyNotFoundException() {
        super("Cannot find warranty information");
    }
}

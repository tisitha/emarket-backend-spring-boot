package com.tisitha.emarket.exception;

public class ProvinceNotFoundException extends ResourceNotFoundException{
    public ProvinceNotFoundException() {
        super("Cannot find province information");
    }
}

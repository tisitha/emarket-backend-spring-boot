package com.tisitha.emarket.exception;

public class OtpNotFoundException extends ResourceNotFoundException{

    public OtpNotFoundException() {
        super("Cannot find OTP information");
    }
}

package com.tisitha.emarket.exception;

public class OtpExpiredException extends RuntimeException{

    public OtpExpiredException(){
        super("Otp has Expired");
    }
}

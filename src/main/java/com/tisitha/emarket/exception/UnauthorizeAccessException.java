package com.tisitha.emarket.exception;

public class UnauthorizeAccessException extends RuntimeException{

    public UnauthorizeAccessException(){
        super("Cannot access");
    }
}

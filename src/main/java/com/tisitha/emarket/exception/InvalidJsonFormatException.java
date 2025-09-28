package com.tisitha.emarket.exception;

public class InvalidJsonFormatException extends RuntimeException{

    public InvalidJsonFormatException(String message){
        super(message);
    }
}

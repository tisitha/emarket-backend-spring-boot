package com.tisitha.emarket.exception;

public class EmailTakenException extends RuntimeException{

    public EmailTakenException(){
        super("Email is taken");
    }
}

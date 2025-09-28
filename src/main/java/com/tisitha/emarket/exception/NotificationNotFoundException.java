package com.tisitha.emarket.exception;

public class NotificationNotFoundException extends RuntimeException{

    public NotificationNotFoundException(){
        super("Cannot find notification information");
    }
}

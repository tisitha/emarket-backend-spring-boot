package com.tisitha.emarket.exception;

public class UserNotFoundException extends ResourceNotFoundException{

    public UserNotFoundException(){
        super("Cannot find user information");
    }
}

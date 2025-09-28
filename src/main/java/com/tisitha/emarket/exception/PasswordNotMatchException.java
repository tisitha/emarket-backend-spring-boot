package com.tisitha.emarket.exception;

public class PasswordNotMatchException extends RuntimeException{

    public PasswordNotMatchException(){
        super("Passwords are not matching");
    }
}

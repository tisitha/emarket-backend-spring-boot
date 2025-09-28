package com.tisitha.emarket.exception;

public class ReviewNotFoundException extends ResourceNotFoundException{
    public ReviewNotFoundException() {
        super("Cannot find review information");
    }
}

package com.tisitha.emarket.exception;

public class QuestionNotFoundException extends ResourceNotFoundException{

    public QuestionNotFoundException() {
        super("Cannot find Question information");
    }
}

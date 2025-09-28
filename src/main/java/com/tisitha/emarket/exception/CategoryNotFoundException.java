package com.tisitha.emarket.exception;

public class CategoryNotFoundException extends ResourceNotFoundException{

    public CategoryNotFoundException(){
        super("Cannot find category information");
    }
}

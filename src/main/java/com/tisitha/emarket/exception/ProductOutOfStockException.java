package com.tisitha.emarket.exception;

public class ProductOutOfStockException extends RuntimeException{

    public ProductOutOfStockException(){
        super("OutOfStock");
    }
}

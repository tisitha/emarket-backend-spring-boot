package com.tisitha.emarket.exception;

public class SupabaseUploadingErrorException extends RuntimeException{

    public SupabaseUploadingErrorException(String message){
        super(message);
    }
}

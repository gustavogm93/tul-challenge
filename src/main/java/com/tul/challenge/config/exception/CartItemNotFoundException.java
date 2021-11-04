package com.tul.challenge.config.exception;

public class CartItemNotFoundException extends RuntimeException{

    public CartItemNotFoundException(){}

    public CartItemNotFoundException(String message) {
        super(message);
    }
}

package com.tul.challenge.config.exception;

public class ShoppingCartNotHaveCartItemException extends RuntimeException{

    public ShoppingCartNotHaveCartItemException(){}

    public ShoppingCartNotHaveCartItemException(String message) {
        super(message);
    }
}

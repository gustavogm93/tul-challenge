package com.tul.challenge.config.exception;

public class ShoppingCartHasStateCompletedException extends RuntimeException {

    public ShoppingCartHasStateCompletedException(){}

    public ShoppingCartHasStateCompletedException(String message) {
        super(message);
    }
}

package com.tul.challenge.config.exception;

public class ShoppingCartEmptyException extends RuntimeException {

    public ShoppingCartEmptyException(){}

    public ShoppingCartEmptyException(String message) {
        super(message);
    }
}

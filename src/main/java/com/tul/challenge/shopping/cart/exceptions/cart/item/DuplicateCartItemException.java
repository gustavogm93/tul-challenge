package com.tul.challenge.shopping.cart.exceptions.cart.item;

public class DuplicateCartItemException extends RuntimeException{

    public DuplicateCartItemException(){}

    public DuplicateCartItemException(String message) {
        super(message);
    }
}

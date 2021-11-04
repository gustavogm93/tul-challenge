package com.tul.challenge.shopping.cart.exceptions.shopping.cart;

public class ShoppingCartEmptyException extends RuntimeException {

    public ShoppingCartEmptyException(){}

    public ShoppingCartEmptyException(String message) {
        super(message);
    }
}

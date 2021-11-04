package com.tul.challenge.shopping.cart.exceptions.shopping.cart;

public class ShoppingCartHasStateCompletedException extends RuntimeException {

    public ShoppingCartHasStateCompletedException(){}

    public ShoppingCartHasStateCompletedException(String message) {
        super(message);
    }
}

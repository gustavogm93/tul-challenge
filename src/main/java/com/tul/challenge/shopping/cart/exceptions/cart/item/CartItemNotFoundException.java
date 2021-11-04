package com.tul.challenge.shopping.cart.exceptions.cart.item;

public class CartItemNotFoundException extends RuntimeException{

    public CartItemNotFoundException(){}

    public CartItemNotFoundException(String message) {
        super(message);
    }
}

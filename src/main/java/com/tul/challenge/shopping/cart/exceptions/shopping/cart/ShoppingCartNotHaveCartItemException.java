package com.tul.challenge.shopping.cart.exceptions.shopping.cart;

public class ShoppingCartNotHaveCartItemException extends RuntimeException{

    public ShoppingCartNotHaveCartItemException(){}

    public ShoppingCartNotHaveCartItemException(String message) {
        super(message);
    }
}

package com.tul.challenge.shopping.cart.exceptions.shopping.cart;

public class ShoppingCartNotFoundException  extends RuntimeException{

    public ShoppingCartNotFoundException(){}

    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}

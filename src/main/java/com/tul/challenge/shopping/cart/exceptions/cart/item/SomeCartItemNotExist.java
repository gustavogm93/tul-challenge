package com.tul.challenge.shopping.cart.exceptions.cart.item;

public class SomeCartItemNotExist extends RuntimeException {

    public SomeCartItemNotExist() {
        super("Some Cart item doesn't exist");
    }
}

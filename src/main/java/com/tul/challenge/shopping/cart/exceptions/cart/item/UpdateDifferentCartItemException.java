package com.tul.challenge.shopping.cart.exceptions.cart.item;

public class UpdateDifferentCartItemException  extends RuntimeException {

    public UpdateDifferentCartItemException(){}

    public UpdateDifferentCartItemException(String message) {
        super(message);
    }
}

package com.tul.challenge.shopping.cart.exceptions.cart.item;

public class AnotherCartItemHasTheSameProductException extends RuntimeException {

    public AnotherCartItemHasTheSameProductException(){
        super("Another cart item has the same product");
    }

    public AnotherCartItemHasTheSameProductException(String message) {
        super(message);
    }
}

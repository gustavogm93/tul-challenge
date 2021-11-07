package com.tul.challenge.shopping.cart.exceptions.cart.item;

import java.sql.SQLIntegrityConstraintViolationException;

public class AnotherCartItemHasTheSameProductException extends SQLIntegrityConstraintViolationException {

    public AnotherCartItemHasTheSameProductException(){
        super("Another cart item has the same product");
    }

    public AnotherCartItemHasTheSameProductException(String message) {
        super(message);
    }
}

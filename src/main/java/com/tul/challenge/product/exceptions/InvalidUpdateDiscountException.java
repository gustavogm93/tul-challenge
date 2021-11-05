package com.tul.challenge.product.exceptions;

public class InvalidUpdateDiscountException extends RuntimeException {

    public InvalidUpdateDiscountException(){}

    public InvalidUpdateDiscountException(String message) {
        super(message);
    }
}

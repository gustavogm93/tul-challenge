package com.tul.challenge.config.exception;

public class InvalidUpdateDiscountException extends RuntimeException {

    public InvalidUpdateDiscountException(){}

    public InvalidUpdateDiscountException(String message) {
        super(message);
    }
}

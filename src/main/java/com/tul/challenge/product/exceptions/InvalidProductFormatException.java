package com.tul.challenge.product.exceptions;

public class InvalidProductFormatException extends RuntimeException {

    public InvalidProductFormatException(){}

    public InvalidProductFormatException(String message) {
        super(message);
    }
}

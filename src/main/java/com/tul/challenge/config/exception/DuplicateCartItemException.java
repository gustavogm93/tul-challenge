package com.tul.challenge.config.exception;

public class DuplicateCartItemException extends RuntimeException{

    public DuplicateCartItemException(){}

    public DuplicateCartItemException(String message) {
        super(message);
    }
}

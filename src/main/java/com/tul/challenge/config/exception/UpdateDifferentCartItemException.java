package com.tul.challenge.config.exception;

public class UpdateDifferentCartItemException  extends RuntimeException {

    public UpdateDifferentCartItemException(){}

    public UpdateDifferentCartItemException(String message) {
        super(message);
    }
}

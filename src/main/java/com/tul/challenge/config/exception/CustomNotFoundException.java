package com.tul.challenge.config.exception;


public class CustomNotFoundException extends NotFoundException {

    public CustomNotFoundException(){}

    public CustomNotFoundException(Class<?> className) {
        super(String.format("%s not found", className.getSimpleName()));
    }
}

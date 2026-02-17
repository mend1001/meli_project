package com.marketplace.domain.exception;

public class NoSuchResourceFoundException extends RuntimeException {

    public NoSuchResourceFoundException(String message) {
        super(message);
    }
}

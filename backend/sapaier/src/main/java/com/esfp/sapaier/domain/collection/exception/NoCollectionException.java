package com.esfp.sapaier.domain.collection.exception;

public class NoCollectionException extends RuntimeException{
    public NoCollectionException(String message) {
        super(message);
    }

    public NoCollectionException(String message, Throwable cause) {
        super(message, cause);
    }

}

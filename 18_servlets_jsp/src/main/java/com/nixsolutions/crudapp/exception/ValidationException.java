package com.nixsolutions.crudapp.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable e) {
        super(e);
    }
}
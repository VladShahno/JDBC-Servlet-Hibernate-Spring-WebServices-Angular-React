package com.nixsolutions.crudapp.exception;

public class UserPasswordEqualsException extends FormProcessingException {

    private static final String attributeName = "passwordError";

    public UserPasswordEqualsException(String message) {
        super(message, attributeName);
    }
}
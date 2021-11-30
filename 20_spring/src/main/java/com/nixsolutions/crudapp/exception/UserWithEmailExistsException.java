package com.nixsolutions.crudapp.exception;

public class UserWithEmailExistsException extends FormProcessingException {

    private static final String attributeName = "emailError";

    public UserWithEmailExistsException(String message) {
        super(message, attributeName);
    }
}
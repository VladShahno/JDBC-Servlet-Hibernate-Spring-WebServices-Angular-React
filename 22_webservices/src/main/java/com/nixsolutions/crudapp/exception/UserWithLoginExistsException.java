package com.nixsolutions.crudapp.exception;

public class UserWithLoginExistsException extends FormProcessingException {

    private static final String attributeName = "loginError";

    public UserWithLoginExistsException(String message) {
        super(message, attributeName);
    }
}
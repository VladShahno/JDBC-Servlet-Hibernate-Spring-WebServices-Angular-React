package com.nixsolutions.crudapp.exception;

public class UserLoginConstraintException extends FormProcessingException {

    private static final String attributeName = "constraintLoginError";

    public UserLoginConstraintException(String message) {
        super(message, attributeName);
    }
}

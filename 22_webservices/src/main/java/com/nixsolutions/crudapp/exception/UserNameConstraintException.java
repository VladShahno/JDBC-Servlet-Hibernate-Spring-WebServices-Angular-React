package com.nixsolutions.crudapp.exception;

public class UserNameConstraintException extends FormProcessingException {

    private static final String attributeName = "nameError";

    public UserNameConstraintException(String message) {
        super(message, attributeName);
    }
}

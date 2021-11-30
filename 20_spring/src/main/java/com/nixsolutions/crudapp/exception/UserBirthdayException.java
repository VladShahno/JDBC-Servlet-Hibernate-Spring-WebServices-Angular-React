package com.nixsolutions.crudapp.exception;

public class UserBirthdayException extends FormProcessingException {

    private static final String attributeName = "dateError";

    public UserBirthdayException(String message) {
        super(message, attributeName);
    }
}
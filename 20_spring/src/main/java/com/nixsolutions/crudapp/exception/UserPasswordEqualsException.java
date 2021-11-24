package com.nixsolutions.crudapp.exception;

public class UserPasswordEqualsException extends Exception {

    public UserPasswordEqualsException(String message) {
        super(message);
    }
}
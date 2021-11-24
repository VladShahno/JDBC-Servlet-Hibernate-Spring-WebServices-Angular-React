package com.nixsolutions.crudapp.exception;

public class UserWithEmailExistsException extends Exception {

    public UserWithEmailExistsException(String message) {
        super(message);
    }
}
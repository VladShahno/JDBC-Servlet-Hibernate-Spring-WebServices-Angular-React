package com.nixsolutions.crudapp.exception;

public class UserWithLoginExistsException extends Exception {

    public UserWithLoginExistsException(String message) {
        super(message);
    }
}
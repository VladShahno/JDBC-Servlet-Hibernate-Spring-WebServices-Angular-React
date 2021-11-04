package com.nixsolutions.crudapp.exception;

public class ConnectionException extends RuntimeException {
    public ConnectionException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}

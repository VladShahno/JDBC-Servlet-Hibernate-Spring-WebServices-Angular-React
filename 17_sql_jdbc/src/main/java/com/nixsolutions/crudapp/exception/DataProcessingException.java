package com.nixsolutions.crudapp.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataProcessingException(Exception e) {
        super(e);
    }
}

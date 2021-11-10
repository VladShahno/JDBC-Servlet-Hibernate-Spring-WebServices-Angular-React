package com.nixsolutions.crudapp.exception;

public class DataProcessingException extends RuntimeException {

    public DataProcessingException(Exception e) {
        super(e);
    }
}
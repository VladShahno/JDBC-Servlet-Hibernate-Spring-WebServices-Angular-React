package com.nixsolutions.crudapp.exception;

public class FormProcessingException extends RuntimeException {

    private final String attributeName;

    public FormProcessingException(String message, String attributeName) {
        super(message);
        this.attributeName = attributeName;
    }

    public String getAttributeName() {
        return attributeName;
    }
}
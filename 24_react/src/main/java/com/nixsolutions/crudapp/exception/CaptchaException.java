package com.nixsolutions.crudapp.exception;

public class CaptchaException extends FormProcessingException {

    private static final String attributeName = "captchaError";

    public CaptchaException(String message) {
        super(message, attributeName);
    }
}
package com.nixsolutions.crudapp.service;

public interface CaptchaService {

    boolean verify(String captcha);
}
package com.nixsolutions.crudapp.util;

import com.nixsolutions.crudapp.exception.FormProcessingException;
import com.nixsolutions.crudapp.exception.UserBirthdayException;
import com.nixsolutions.crudapp.exception.UserPasswordEqualsException;
import com.nixsolutions.crudapp.exception.UserWithEmailExistsException;
import com.nixsolutions.crudapp.exception.UserWithLoginExistsException;
import org.springframework.ui.Model;

public class UserFormValidator {

    public static void validate(Model model, Exception e)
            throws FormProcessingException {
        if (e.getClass().equals(UserWithLoginExistsException.class)) {
            model.addAttribute("loginError", e.getMessage());
            throw new FormProcessingException(e.getMessage());
        }
        if (e.getClass().equals(UserWithEmailExistsException.class)) {
            model.addAttribute("emailError", e.getMessage());
            throw new FormProcessingException(e.getMessage());
        }
        if (e.getClass().equals(UserBirthdayException.class)) {
            model.addAttribute("dateError", e.getMessage());
            throw new FormProcessingException(e.getMessage());
        }
        if (e.getClass().equals(UserPasswordEqualsException.class)) {
            model.addAttribute("passwordError", e.getMessage());
            throw new FormProcessingException(e.getMessage());
        }
    }
}
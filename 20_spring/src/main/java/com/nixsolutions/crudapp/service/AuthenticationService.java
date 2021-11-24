package com.nixsolutions.crudapp.service;

import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.exception.UserBirthdayException;
import com.nixsolutions.crudapp.exception.UserPasswordEqualsException;
import com.nixsolutions.crudapp.exception.UserWithEmailExistsException;
import com.nixsolutions.crudapp.exception.UserWithLoginExistsException;

public interface AuthenticationService {
    User register(User user)
            throws UserWithLoginExistsException, UserWithEmailExistsException,
            UserBirthdayException, UserPasswordEqualsException;
}
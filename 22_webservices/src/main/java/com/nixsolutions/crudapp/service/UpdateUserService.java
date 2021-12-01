package com.nixsolutions.crudapp.service;

import com.nixsolutions.crudapp.data.UserDto;
import com.nixsolutions.crudapp.exception.UserBirthdayException;
import com.nixsolutions.crudapp.exception.UserPasswordEqualsException;
import com.nixsolutions.crudapp.exception.UserWithEmailExistsException;

public interface UpdateUserService {

    UserDto update(UserDto user)
            throws UserWithEmailExistsException, UserBirthdayException,
            UserPasswordEqualsException;
}
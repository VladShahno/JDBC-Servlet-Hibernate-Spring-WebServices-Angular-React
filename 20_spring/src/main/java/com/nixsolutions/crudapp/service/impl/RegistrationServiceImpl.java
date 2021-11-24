package com.nixsolutions.crudapp.service.impl;

import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.exception.UserBirthdayException;
import com.nixsolutions.crudapp.exception.UserPasswordEqualsException;
import com.nixsolutions.crudapp.exception.UserWithEmailExistsException;
import com.nixsolutions.crudapp.exception.UserWithLoginExistsException;
import com.nixsolutions.crudapp.service.AuthenticationService;
import com.nixsolutions.crudapp.service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class RegistrationServiceImpl implements AuthenticationService {

    private final UserService userService;

    public RegistrationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User register(User user)
            throws UserWithLoginExistsException, UserWithEmailExistsException,
            UserBirthdayException, UserPasswordEqualsException {

        if (userService.findByLogin(user.getLogin()) != null) {
            throw new UserWithLoginExistsException(
                    "User with such login had been already created!");
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            throw new UserWithEmailExistsException(
                    "User with such email had been already created!");
        }
        if (user.getBirthday() == null || user.getBirthday()
                .before(Date.valueOf("1900-01-01")) || user.getBirthday()
                .after(new Date(new java.util.Date().getTime()))) {
            throw new UserBirthdayException(
                    "Date is incorrect, please enter the right date!");
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            throw new UserPasswordEqualsException("Password is not match!");
        }
        userService.create(user);
        return user;
    }
}
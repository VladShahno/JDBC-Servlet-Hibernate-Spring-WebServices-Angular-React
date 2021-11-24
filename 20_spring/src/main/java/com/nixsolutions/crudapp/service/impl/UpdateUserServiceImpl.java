package com.nixsolutions.crudapp.service.impl;

import com.nixsolutions.crudapp.data.UserDto;
import com.nixsolutions.crudapp.exception.UserBirthdayException;
import com.nixsolutions.crudapp.exception.UserPasswordEqualsException;
import com.nixsolutions.crudapp.exception.UserWithEmailExistsException;
import com.nixsolutions.crudapp.service.UpdateUserService;
import com.nixsolutions.crudapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class UpdateUserServiceImpl implements UpdateUserService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserServiceImpl(UserService userService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto update(UserDto user)
            throws UserWithEmailExistsException, UserBirthdayException,
            UserPasswordEqualsException {

        if (userService.existsByEmail(user.getEmail())
                && !userService.findByLogin(user.getLogin()).getEmail()
                .equals(user.getEmail())) {
            throw new UserWithEmailExistsException(
                    "User with such email had been already created!");
        }
        if (!userService.findByLogin(user.getLogin()).getEmail()
                .equals(user.getEmail())) {
            if (user.getEmail()
                    .equals(userService.findByEmail(user.getEmail())))
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
        if (!(user.getPassword().equals(userService.findByLogin(user.getLogin())
                .getPassword()))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.update(user);
        return user;
    }
}

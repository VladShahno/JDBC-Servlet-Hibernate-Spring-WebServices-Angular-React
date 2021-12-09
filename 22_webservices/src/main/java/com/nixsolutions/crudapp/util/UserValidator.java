package com.nixsolutions.crudapp.util;

import com.nixsolutions.crudapp.data.UserDtoForCreate;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.exception.UserBirthdayException;
import com.nixsolutions.crudapp.exception.UserLoginConstraintException;
import com.nixsolutions.crudapp.exception.UserNameConstraintException;
import com.nixsolutions.crudapp.exception.UserPasswordEqualsException;
import com.nixsolutions.crudapp.exception.UserWithEmailExistsException;
import com.nixsolutions.crudapp.exception.UserWithLoginExistsException;
import com.nixsolutions.crudapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserValidator {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserValidator(UserService userService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public void validateUpdate(UserDtoForCreate userDtoForCreate) {

        validateBirthday(userDtoForCreate.getBirthday());

        validateName(userDtoForCreate.getFirstName());

        validateName(userDtoForCreate.getLastName());

        if (userService.existsByEmail(userDtoForCreate.getEmail())
                && !userService.findByLogin(userDtoForCreate.getLogin())
                .getEmail().equals(userDtoForCreate.getEmail())) {
            throw new UserWithEmailExistsException(
                    "User with such email had been already created!");
        }
        if (!userService.findByLogin(userDtoForCreate.getLogin()).getEmail()
                .equals(userDtoForCreate.getEmail())) {
            if (userDtoForCreate.getEmail().equals(userService.findByEmail(
                    userDtoForCreate.getEmail())))
                throw new UserWithEmailExistsException(
                        "User with such email had been already created!");
        }
        if (!userDtoForCreate.getPassword()
                .equals(userDtoForCreate.getPasswordConfirm())
                || userDtoForCreate.getPasswordConfirm().isBlank()
                || userDtoForCreate.getPasswordConfirm().isBlank()) {
            throw new UserPasswordEqualsException(
                    "Password is not match or empty!");
        }
        if (!(userDtoForCreate.getPassword()
                .equals(userService.findByLogin(userDtoForCreate.getLogin())
                        .getPassword()))) {
            userDtoForCreate.setPassword(
                    passwordEncoder.encode(userDtoForCreate.getPassword()));
        }
    }

    public void validateCreate(User user) {

        validateBirthday(user.getBirthday());

        validateName(user.getFirstName());

        validateName(user.getLastName());

        validateLoginConstraint(user.getLogin());

        if (userService.findByLogin(user.getLogin()) != null) {
            throw new UserWithLoginExistsException(
                    "User with such login had been already created!");
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            throw new UserWithEmailExistsException(
                    "User with such email had been already created!");
        }
        if ((!user.getPassword().equals(user.getPasswordConfirm())
                || user.getPassword().isBlank() || user.getPasswordConfirm()
                .isBlank())) {
            throw new UserPasswordEqualsException(
                    "Password is not match or empty!");
        }
    }

    private void validateBirthday(Date date) {
        if (date == null || date.before(new Date(1900 - 01 - 01)) || date.after(
                new Date(new Date().getTime()))) {
            throw new UserBirthdayException(
                    "Date is incorrect, please enter the right date!");
        }
    }

    private void validateName(String name) {
        if (name.isBlank() || name.length() > 40 || name.length() < 2) {
            throw new UserNameConstraintException(
                    " Wrong Name or Surname length must be between [2-40] letters!");
        }
    }

    private void validateLoginConstraint(String login) {
        if (login.isBlank() || login.length() > 10 || login.length() < 2) {
            throw new UserLoginConstraintException(
                    "Login length must be between [2-40] letters!");
        }
    }
}
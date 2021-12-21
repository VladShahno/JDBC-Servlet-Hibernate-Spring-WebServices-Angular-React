package com.nixsolutions.crudapp.mapper;

import com.nixsolutions.crudapp.data.PublicUserDto;
import com.nixsolutions.crudapp.data.UserDtoForCreate;
import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.service.RoleService;
import com.nixsolutions.crudapp.service.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
public class UserMapper {

    private final RoleService roleService;

    private final UserService userService;

    public UserMapper(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public User fromDtoForCreateToUserUpdate(
            UserDtoForCreate userDtoForCreate) {

        Optional<User> optionalUser = Optional.ofNullable(
                userService.findByLogin(userDtoForCreate.getLogin()));
        if (optionalUser.isEmpty()) {
            throw new RuntimeException();
        }
        User user = optionalUser.get();
        user.setLogin(userDtoForCreate.getLogin());

        if (userDtoForCreate.getPassword() != null) {
            user.setPassword(userDtoForCreate.getPassword());
            user.setPasswordConfirm(userDtoForCreate.getPasswordConfirm());
        }
        user.setEmail(userDtoForCreate.getEmail());
        user.setBirthday(userDtoForCreate.getBirthday());
        user.setFirstName(userDtoForCreate.getFirstName());
        user.setLastName(userDtoForCreate.getLastName());

        Optional<Role> optionalRole = Optional.ofNullable(
                roleService.findByName(userDtoForCreate.getRole()));

        if (optionalRole.isEmpty()) {
            throw new RuntimeException();
        }

        user.setRole(optionalRole.get());
        return user;
    }

    public PublicUserDto publicUserDtoFromUser(User user) {

        PublicUserDto userDto = new PublicUserDto();
        userDto.setAge(ChronoUnit.YEARS.between(
                user.getBirthday().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate(), LocalDate.now()));
        userDto.setLogin(user.getLogin());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole().getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public UserDtoForCreate userDtoForCreateFromUser(User user) {

        UserDtoForCreate userDto = new UserDtoForCreate();
        userDto.setLogin(user.getLogin());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPassword(user.getPassword());
        userDto.setPasswordConfirm(user.getPassword());
        userDto.setRole(user.getRole().getName());
        userDto.setEmail(user.getEmail());
        userDto.setBirthday(user.getBirthday());
        return userDto;
    }

    public User userFromUserDtoForCreate(UserDtoForCreate userDtoForCreate) {

        return new User(userDtoForCreate.getLogin(),
                userDtoForCreate.getPassword(),
                userDtoForCreate.getPasswordConfirm(),
                userDtoForCreate.getEmail(), userDtoForCreate.getFirstName(),
                userDtoForCreate.getLastName(), userDtoForCreate.getBirthday(),
                roleService.findByName(userDtoForCreate.getRole()));
    }
}
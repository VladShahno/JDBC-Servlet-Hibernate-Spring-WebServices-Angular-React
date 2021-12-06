package com.nixsolutions.crudapp.service;

import com.nixsolutions.crudapp.data.UserDto;
import com.nixsolutions.crudapp.entity.User;

import java.util.List;

public interface UserService {

    void create(User user);

    void update(UserDto userDto);

    void remove(User user);

    List<User> findAll();

    User findByLogin(String login);

    User findByEmail(String email);

    User findById(Long id);

    boolean existsByEmail(String email);

    User convert(UserDto dto);
}
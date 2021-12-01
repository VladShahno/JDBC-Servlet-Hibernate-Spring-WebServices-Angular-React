package com.nixsolutions.crudapp.service;

import com.nixsolutions.crudapp.entity.User;

import java.util.List;

public interface UserService {

    void create(User user);

    void update(User user);

    void remove(User user);

    List<User> findAll();

    User findByLogin(String login);

    User findByEmail(String email);

    User findById(Long id);

}
package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.entity.User;

import java.util.List;

public interface UserDao extends Dao<User> {

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);
}

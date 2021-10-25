package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.entity.User;

import java.util.List;

public interface UserDao extends BasicDao<User> {

    @Override
    void create(User entity);

    @Override
    void update(User entity);

    @Override
    void remove(User entity);

    @Override
    List<User> findAll();

    @Override
    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);
}

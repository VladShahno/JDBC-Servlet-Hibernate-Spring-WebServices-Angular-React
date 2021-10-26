package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends BasicDao<User> {

    @Override
    void create(User user) throws SQLException;

    @Override
    void update(User user);

    @Override
    void remove(User user);

    @Override
    List<User> findAll();

    @Override
    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);
}

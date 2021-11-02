package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends Dao<User> {

    void create(User user) throws SQLException;

    void update(User user) throws SQLException;

    void remove(User user) throws SQLException;

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);
}

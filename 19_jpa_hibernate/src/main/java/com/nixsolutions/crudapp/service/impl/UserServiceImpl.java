package com.nixsolutions.crudapp.service.impl;

import com.nixsolutions.crudapp.dao.UserDao;
import com.nixsolutions.crudapp.dao.impl.HibernateUserDao;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new HibernateUserDao();
    }

    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void remove(User user) {
        userDao.remove(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public boolean existsByLogin(String login) {
        return userDao.existsByLogin(login);
    }
}
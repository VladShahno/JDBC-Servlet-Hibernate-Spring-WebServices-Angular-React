package com.nixsolutions.crudapp;

import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.dao.UserDao;
import com.nixsolutions.crudapp.dao.impl.JdbcRoleDaoImpl;
import com.nixsolutions.crudapp.dao.impl.JdbcUserDaoImpl;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) throws IOException, SQLException {

        UserDao userDao = new JdbcUserDaoImpl();
        System.out.println(userDao.findAll());
        RoleDao roleDao = new JdbcRoleDaoImpl();
        System.out.println(roleDao.findByName("ADMIN"));
        System.out.println(roleDao.findByName("USER"));
    }
}

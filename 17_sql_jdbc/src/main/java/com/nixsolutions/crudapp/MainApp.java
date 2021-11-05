package com.nixsolutions.crudapp;

import com.nixsolutions.crudapp.dao.impl.JdbcRoleDaoImpl;
import com.nixsolutions.crudapp.dao.impl.JdbcUserDaoImpl;
import com.nixsolutions.crudapp.entity.Role;

public class MainApp {
    public static void main(String[] args) {
        JdbcRoleDaoImpl jdbcRoleDao = new JdbcRoleDaoImpl();
        JdbcUserDaoImpl jdbcUserDao = new JdbcUserDaoImpl();
        jdbcRoleDao.create(new Role(1L,"SS"));
    }
}

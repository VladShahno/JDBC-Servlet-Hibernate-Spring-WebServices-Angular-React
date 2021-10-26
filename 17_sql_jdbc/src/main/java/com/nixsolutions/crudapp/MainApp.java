package com.nixsolutions.crudapp;

import com.nixsolutions.crudapp.dao.impl.JdbcRoleDaoImpl;
import com.nixsolutions.crudapp.dao.impl.JdbcUserDaoImpl;
import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.util.DataBaseCreator;

import java.sql.Date;
import java.sql.SQLException;

public class MainApp {

    public static void main(String[] args) throws SQLException {

        JdbcUserDaoImpl jdbcUserDao = new JdbcUserDaoImpl();
        JdbcRoleDaoImpl jdbcRoleDao = new JdbcRoleDaoImpl();

        DataBaseCreator dataBaseCreator = new DataBaseCreator();
        dataBaseCreator.createTableSql();

        Date date = new Date(1999 / 03 / 10);

        Role role;
        new User();
        User USER;

        Role roleAdmin = new Role("Mod");
        Role roleUser = new Role("User");
        jdbcRoleDao.create(roleAdmin);
        jdbcRoleDao.create(roleUser);

        role = jdbcRoleDao.findByName("User");

        USER = jdbcUserDao.findByEmail("email");

        User user = new User("Vlados", "1234", "email", "Vlad", "Shakhno", date,
                role.getId());

        User user1 = new User("Vlados", "1234", "email1", "Vlad", "Shakhno", date,
                role.getId());


        //        jdbcUserDao.create(user);
        //        Role role1 = new Role("JOO");
        //jdbcRoleDao.update(roleAdmin);

        Role r2 = new Role();

        r2 =  jdbcRoleDao.findById(1L);
        System.out.println(r2);
        //jdbcRoleDao.remove(r2);
        System.out.println(r2);
        System.out.println(USER);

       // System.out.println(role.getName());
    }
}

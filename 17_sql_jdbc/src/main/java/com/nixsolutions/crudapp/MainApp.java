package com.nixsolutions.crudapp;

import com.nixsolutions.crudapp.dao.impl.JdbcRoleDaoImpl;
import com.nixsolutions.crudapp.dao.impl.JdbcUserDaoImpl;
import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.util.DataBaseCreator;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MainApp {

    public static void main(String[] args) throws SQLException {

        JdbcUserDaoImpl jdbcUserDao = new JdbcUserDaoImpl();
        JdbcRoleDaoImpl jdbcRoleDao = new JdbcRoleDaoImpl();

        DataBaseCreator dataBaseCreator = new DataBaseCreator();
        //dataBaseCreator.createTableSql();

        Date date = new Date(1999 / 03 / 10);

        Role role;
        new User();
        User USER;

        Role roleAdmin = new Role("Mod");
        Role roleUser = new Role("User");
        jdbcRoleDao.create(roleAdmin);
        jdbcRoleDao.create(roleUser);

        //role = jdbcRoleDao.findByName("User");

        User user = new User(1L,"Vlados", "1234", "email", "Vlad", "Shakhno", date,
                roleUser.getId());

        User user1 = new User("NIX", "1234", "email1", "Vlad", "Shakhno", date,
                roleAdmin.getId());

        User updatedUser = new User("WAS UPDATED", "1234", "email", "Vlad",
                "Shakhno", date, roleUser.getId());

//        jdbcUserDao.create(user);
//        jdbcUserDao.create(user1);
//        jdbcUserDao.create(updatedUser);

       jdbcUserDao.remove(user);
       // jdbcUserDao.update(user1);
    }
}

package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.entity.Role;

import java.sql.SQLException;

public interface RoleDao extends Dao<Role> {

    void create(Role entity) throws SQLException;

    void update(Role entity) throws SQLException;

    void remove(Role entity) throws SQLException;

    Role findByName(String name) throws SQLException;
}

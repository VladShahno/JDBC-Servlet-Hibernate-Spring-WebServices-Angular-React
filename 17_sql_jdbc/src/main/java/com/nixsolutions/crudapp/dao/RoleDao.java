package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.entity.Role;

import java.sql.SQLException;

public interface RoleDao extends BasicDao<Role> {

    @Override
    void create(Role entity) throws SQLException;

    @Override
    void update(Role entity) throws SQLException;

    @Override
    void remove(Role entity) throws SQLException;

    Role findByName(String name);
}

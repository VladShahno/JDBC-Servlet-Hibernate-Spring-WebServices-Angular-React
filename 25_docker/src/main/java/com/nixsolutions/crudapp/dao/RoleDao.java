package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.entity.Role;

import java.sql.SQLException;

public interface RoleDao extends Dao<Role> {
    Role findByName(String name) throws SQLException;
}

package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.entity.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleDao extends BasicDao<Role> {

    @Override
    void create(Role entity) throws SQLException;

    @Override
    void update(Role entity) throws SQLException;

    @Override
    void remove(Role entity) throws SQLException;

    @Override
    List<Role> findAll();

    @Override
    Role findById(Long id);

    Role findByName(String name);
}

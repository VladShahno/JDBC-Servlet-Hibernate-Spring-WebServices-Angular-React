package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.entity.Role;

import java.util.List;

public interface RoleDao extends BasicDao<Role> {

    @Override
    void create(Role entity);

    @Override
    void update(Role entity);

    @Override
    void remove(Role entity);

    @Override
    List<Role> findAll();

    @Override
    Role findById(Long id);

    Role findByName(String name);
}

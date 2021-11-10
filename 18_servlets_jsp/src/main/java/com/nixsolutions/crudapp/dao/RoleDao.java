package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.entity.Role;

public interface RoleDao extends Dao<Role> {

    Role findByName(String name);

    boolean existsById(Long id);

    boolean existsByName(String name);
}

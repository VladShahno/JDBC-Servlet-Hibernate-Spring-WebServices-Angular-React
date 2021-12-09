package com.nixsolutions.crudapp.service;

import com.nixsolutions.crudapp.entity.Role;

import java.util.List;

public interface RoleService {

    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);

    Role findById(Long id);

    List<Role> findAll();
}
package com.nixsolutions.crudapp.service.impl;

import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.dao.impl.JdbcRoleDaoImpl;
import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.exception.ValidationException;
import com.nixsolutions.crudapp.service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl() {
        roleDao = new JdbcRoleDaoImpl();
    }

    @Override
    public void create(Role role) {
        roleDao.create(role);
    }

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public void remove(Role role) {
        roleDao.create(role);
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
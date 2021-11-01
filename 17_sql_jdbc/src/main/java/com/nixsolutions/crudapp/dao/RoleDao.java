package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.entity.Role;

import java.sql.SQLException;

public interface RoleDao extends BasicDao<Role> {

    @Override
    // Точнно ли нужно явно указывать здесь Override? Как у нас работает наследование и дженерики?
    // Если я удалю этот метод в этом интерфейсе, метод с какой сигнатурой будет доступен в классе который имплементирует данный интерфейс?
    // update(<?> ?) ???
    // Результат и обьяснение почему так отпиши в телеграмм
    void update(Role entity) throws SQLException;

    @Override
    void remove(Role entity) throws SQLException;

    Role findByName(String name);
}

package com.nixsolutions.crudapp.dao;

import java.sql.SQLException;

public interface Dao<E> {

    void create(E entity) throws SQLException;

    void update(E entity) throws SQLException;

    void remove(E entity) throws SQLException;
}

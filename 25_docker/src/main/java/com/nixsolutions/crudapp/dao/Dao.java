package com.nixsolutions.crudapp.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Dao<E> {

    void create(E entity) throws SQLException, IOException;

    void update(E entity) throws SQLException, IOException;

    void remove(E entity) throws SQLException, IOException;

    E findById(Long id) throws SQLException;

    List<E> findAll() throws SQLException;

}

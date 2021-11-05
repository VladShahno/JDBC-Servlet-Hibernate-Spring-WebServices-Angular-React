package com.nixsolutions.crudapp.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Dao<E> {

    void create(E entity);

    void update(E entity);

    void remove(E entity);

    E findById(Long id);

    List<E> findAll();
}

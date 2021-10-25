package com.nixsolutions.crudapp.dao;

import java.util.List;

public interface BasicDao<E> {

    void create(E entity);

    void update(E entity);

    void remove(E entity);

    List<E> findAll();

    E findById(Long id);
}

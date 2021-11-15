package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;

public abstract class AbstractDao {
    protected Session getSession() {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }
}

package com.nixsolutions.crudapp.util;

import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Role.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());

        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Exception occurred. Hibernate session factory", e);
        }
        return sessionFactory;
    }
}
package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.UserDao;
import com.nixsolutions.crudapp.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public void remove(User user) {
        sessionFactory.getCurrentSession().remove(user);
    }

    @Override
    public List<User> findAll() {

        Session session = sessionFactory.getCurrentSession();
        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder()
                .createQuery(User.class);
        criteriaQuery.from(User.class);
        return session.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public User findById(Long id) {

        String hqlQuery = "FROM User us WHERE us.id = :id";
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(hqlQuery);
        query.setParameter("id", id);
        return query.uniqueResult();
    }

    @Override
    public User findByLogin(String login) {

        String hqlQuery = "FROM User us WHERE us.login = :login";
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(hqlQuery);
        query.setParameter("login", login);
        return query.uniqueResult();
    }

    @Override
    public User findByEmail(String email) {

        String hqlQuery = "FROM User us WHERE us.email = :email";
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(hqlQuery);
        query.setParameter("email", email);
        return query.uniqueResult();
    }

    @Override
    public boolean existsByEmail(String email) {

        Session session = sessionFactory.getCurrentSession();
        Optional<User> result = session.createQuery(
                        "FROM User us WHERE us.email =:email", User.class)
                .setParameter("email", email).uniqueResultOptional();
        return result.isPresent();
    }

}
package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.UserDao;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.exception.DataProcessingException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public class HibernateUserDao extends AbstractDao implements UserDao {

    private Logger LOGGER = LoggerFactory.getLogger(HibernateUserDao.class);

    @Override
    public void create(User user) {

        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("There is an error while creating user with login: "
                    + user.getLogin());
            throw new DataProcessingException(
                    "There is an error while creating " + "a user with email: "
                            + user.getEmail(), e);
        }
    }

    @Override
    public void update(User user) {

        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error("There is an error while updating user with id: "
                    + user.getId(), e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "There is an error while updating " + "user with id: "
                            + user.getId(), e);
        }
    }

    @Override
    public void remove(User user) {

        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error("There is an error while deleting user with id: "
                    + user.getId(), e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "There is an error while deleting user with id: "
                            + user.getId(), e);
        }
    }

    @Override
    public List<User> findAll() {

        try (Session session = getSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(User.class);
            criteriaQuery.from(User.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            LOGGER.error("There is an error while getting all users from db!");
            throw new DataProcessingException(
                    "There is an error while getting all users from db!", e);
        }
    }

    @Override
    public User findById(Long id) {

        String hqlQuery = "FROM User us WHERE us.id = :id";
        try (Session session = getSession()) {
            Query<User> query = session.createQuery(hqlQuery);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.error("There is an error while getting user via id: " + id,
                    e);
            throw new DataProcessingException(
                    "There is an error while " + "getting user via id: " + id,
                    e);
        }
    }

    @Override
    public User findByLogin(String login) {

        String hqlQuery = "FROM User us WHERE us.login = :login";
        try (Session session = getSession()) {
            Query<User> query = session.createQuery(hqlQuery);
            query.setParameter("login", login);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.error(
                    "There is an error while getting user via login: " + login,
                    e);
            throw new DataProcessingException(
                    "There is an error while " + "getting user via login: "
                            + login, e);
        }
    }

    @Override
    public User findByEmail(String email) {

        String hqlQuery = "FROM User us WHERE us.email = :email";
        try (Session session = getSession()) {
            Query<User> query = session.createQuery(hqlQuery);
            query.setParameter("email", email);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.error(
                    "There is an error while getting user via email: " + email,
                    e);
            throw new DataProcessingException(
                    "There is an error while " + "getting user via email: "
                            + email, e);
        }
    }

    @Override
    public boolean existsByLogin(String login) {
        Session session = getSession();
        Optional<User> result = session.createQuery(
                        "FROM User us WHERE us.login =:login", User.class)
                .setParameter("login", login).uniqueResultOptional();
        return result.isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {

        Session session = getSession();
        Optional<User> result = session.createQuery(
                        "FROM User us WHERE us.email =:email", User.class)
                .setParameter("email", email).uniqueResultOptional();
        return result.isPresent();
    }
}
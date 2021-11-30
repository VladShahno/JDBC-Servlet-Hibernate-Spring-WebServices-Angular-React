package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.exception.DataProcessingException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class HibernateRoleDao extends AbstractDao implements RoleDao {

    private Logger LOGGER = LoggerFactory.getLogger(HibernateRoleDao.class);

    @Override
    public void create(Role role) {

        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("There is an error while creating a role with name: "
                    + role.getName());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "There is an error while creating " + "a role with name: "
                            + role.getName(), e);
        }
    }

    @Override
    public void update(Role role) {

        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.update(role);
            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error("There is an error while updating a role with id: "
                    + role.getId(), e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "There is an error while updating " + "a role with id: "
                            + role.getId(), e);
        }
    }

    @Override
    public void remove(Role role) {

        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.remove(role);
            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error("There is an error while deleting a role with id: "
                    + role.getId(), e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "There is an error while deleting a role with id: "
                            + role.getId(), e);
        }
    }

    @Override
    public Role findByName(String name) {

        String hqlQuery = "FROM Role rl WHERE rl.name = :name";
        try (Session session = getSession()) {
            Query<Role> query = session.createQuery(hqlQuery);
            query.setParameter("name", name);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.error(
                    "There is an error while getting role by name: " + name, e);
            throw new DataProcessingException(
                    "There is an error while getting " + "role by name: "
                            + name, e);
        }
    }

    @Override
    public List<Role> findAll() {

        try (Session session = getSession()) {
            CriteriaQuery<Role> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Role.class);
            criteriaQuery.from(Role.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            LOGGER.error("There is an error while getting all roles from db!");
            throw new DataProcessingException(
                    "There is an error while getting all roles from db!", e);
        }
    }

    @Override
    public Role findById(Long id) {

        String hqlQuery = "FROM Role rl WHERE rl.id = :id";
        try (Session session = getSession()) {
            Query<Role> query = session.createQuery(hqlQuery);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.error("There is an error while getting role by id: " + id,
                    e);
            throw new DataProcessingException(
                    "There is an error while getting " + "role by id: " + id,
                    e);
        }
    }
}
package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Transactional
@Repository
public class RoleDaoImpl implements RoleDao {

    private final SessionFactory sessionFactory;

    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Role role) {
        sessionFactory.getCurrentSession().save(role);
    }

    @Override
    public void update(Role role) {
        sessionFactory.getCurrentSession().update(role);

    }

    @Override
    public void remove(Role role) {
        sessionFactory.getCurrentSession().remove(role);

    }

    @Override
    public Role findByName(String name) {

        String hqlQuery = "FROM Role rl WHERE rl.name = :name";
        Session session = sessionFactory.getCurrentSession();
        Query<Role> query = session.createQuery(hqlQuery);
        query.setParameter("name", name);
        return query.uniqueResult();
    }

    @Override
    public List<Role> findAll() {

        Session session = sessionFactory.getCurrentSession();
        CriteriaQuery<Role> criteriaQuery = session.getCriteriaBuilder()
                .createQuery(Role.class);
        criteriaQuery.from(Role.class);
        return session.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Role findById(Long id) {

        String hqlQuery = "FROM Role rl WHERE rl.id = :id";
        Session session = sessionFactory.getCurrentSession();
        Query<Role> query = session.createQuery(hqlQuery);
        query.setParameter("id", id);
        return query.uniqueResult();
    }
}
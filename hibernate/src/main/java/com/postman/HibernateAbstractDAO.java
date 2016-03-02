package com.postman;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public abstract class HibernateAbstractDAO<T> implements GenericDAO<T> {

    @Autowired
    SessionFactory sessionFactory;

    public abstract Class getObjectClass();

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public T create(T object) throws PersistenceException {
        getCurrentSession().save(object);
        return object;
    }

    public T read(long id) throws PersistenceException {
        return (T)getCurrentSession().get(getObjectClass(), id);
    }

    public void update(T object) throws PersistenceException {
        getCurrentSession().update(object);
    }

    protected void delete(T object) throws PersistenceException {
        getCurrentSession().delete(object);
    }

    public List<T> readAll() throws PersistenceException {
        Criteria criteria = getCurrentSession().createCriteria(getObjectClass());
        return (List<T>)criteria.list();
    }
}

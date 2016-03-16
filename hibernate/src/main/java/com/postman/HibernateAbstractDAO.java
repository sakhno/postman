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

    @Override
    public T create(T object) throws PersistenceException {
        getCurrentSession().save(object);
        return object;
    }

    @Override
    public T read(long id) throws PersistenceException {
        return (T)getCurrentSession().get(getObjectClass(), id);
    }

    @Override
    public void update(T object) throws PersistenceException {
        getCurrentSession().update(object);
    }

    @Override
    public void delete(long id) throws PersistenceException {
        getCurrentSession().delete(read(id));
    }

    @Override
    public List<T> readAll() throws PersistenceException {
        Criteria criteria = getCurrentSession().createCriteria(getObjectClass());
        return (List<T>)criteria.list();
    }
}

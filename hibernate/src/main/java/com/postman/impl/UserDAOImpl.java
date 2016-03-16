package com.postman.impl;

import com.postman.HibernateAbstractDAO;
import com.postman.PersistenceException;
import com.postman.User;
import com.postman.UserDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Repository
public class UserDAOImpl extends HibernateAbstractDAO<User> implements UserDAO {
    @Override
    public Class getObjectClass() {
        return User.class;
    }

    public User getUserByLogin(String login) throws PersistenceException {
        Criteria criteria = getCurrentSession().createCriteria(getObjectClass())
                .add(Restrictions.eq("login", login));
        return (User)criteria.uniqueResult();
    }
}

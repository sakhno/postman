package com.postman.impl;

import com.postman.HibernateAbstractDAO;
import com.postman.PersistenceException;
import com.postman.User;
import com.postman.UserDAO;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class UserDAOImpl extends HibernateAbstractDAO<User> implements UserDAO {
    @Override
    public Class getObjectClass() {
        return User.class;
    }

    public void delete(long id) throws PersistenceException {
        User user = read(id);
        delete(user);
    }
}

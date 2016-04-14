package com.postman;

import com.postman.model.User;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface UserDAO extends GenericDAO<User> {
    User getUserByLogin(String login) throws PersistenceException;
}

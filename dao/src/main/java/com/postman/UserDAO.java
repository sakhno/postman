package com.postman;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface UserDAO extends GenericDAO<User> {
    User getUserByLogin(String login) throws PersistenceException;
}

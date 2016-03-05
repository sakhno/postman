package com.postman;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface UserService {
    User saveUser(User user) throws PersistenceException;
    void deleteUser(long id) throws PersistenceException;
    User findUserById(long id) throws PersistenceException;
    List<User> getAllUsers() throws PersistenceException;
    User getUserByLogin(String login) throws PersistenceException;
}

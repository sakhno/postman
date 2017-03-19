package com.postman;

import com.postman.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface UserService extends UserDetailsService {
    User saveUser(User user) throws PersistenceException;

    void deleteUser(long id) throws PersistenceException;

    User findUserById(long id) throws PersistenceException;

    List<User> getAllUsers() throws PersistenceException;

    User getUserByLogin(String login) throws PersistenceException;

    void verifyEmail(User user, String host) throws PersistenceException;

    User verifyToken(String token) throws PersistenceException, TokenExpiredException;
}

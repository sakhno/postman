package com.postman.impl;

import com.postman.PersistenceException;
import com.postman.User;
import com.postman.UserDAO;
import com.postman.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    public User saveUser(User user) throws PersistenceException{
        if(user.getId()!=0){
            userDAO.update(user);
        }else {
            user = userDAO.create(user);
        }
        return user;
    }

    public void deleteUser(int id)  throws PersistenceException{
        userDAO.delete(id);
    }

    public User findUserById(int id)  throws PersistenceException{
        return userDAO.read(id);
    }

    public List<User> getAllUsers()  throws PersistenceException{
        return userDAO.readAll();
    }

    public User getUserByLogin(String login)  throws PersistenceException{
        return userDAO.getUserByLogin(login);
    }
}

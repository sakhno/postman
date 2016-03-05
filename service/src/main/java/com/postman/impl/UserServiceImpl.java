package com.postman.impl;

import com.postman.PersistenceException;
import com.postman.User;
import com.postman.UserDAO;
import com.postman.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;

    public User saveUser(User user) throws PersistenceException{
        if(user.getId()!=0){
            if(user.getPassword()==null){
                User oldUser = userDAO.read(user.getId());
                user.setPassword(oldUser.getPassword());
            }else {
                user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), null));
            }
            userDAO.update(user);
        }else {
            user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), null));
            user = userDAO.create(user);
        }
        return user;
    }

    public void deleteUser(long id)  throws PersistenceException{
        userDAO.delete(id);
    }

    public User findUserById(long id)  throws PersistenceException{
        return userDAO.read(id);
    }

    public List<User> getAllUsers()  throws PersistenceException{
        return userDAO.readAll();
    }

    public User getUserByLogin(String login)  throws PersistenceException{
        return userDAO.getUserByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Set<GrantedAuthority> roles = new HashSet<>();
        User user;
        try {
            user = getUserByLogin(login);
            if(user==null){
                throw new UsernameNotFoundException("user not found");
            }
        } catch (PersistenceException e) {
            LOGGER.error(e);
            throw new UsernameNotFoundException("error reading user from DB");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roles);
    }
}

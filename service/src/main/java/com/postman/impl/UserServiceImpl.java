package com.postman.impl;

import com.postman.*;
import com.postman.model.User;
import com.postman.model.VerificationToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private static final String BASE_URL_LOCALHOST = "http://localhost:8080/users/confirmation";
    private static final String EMAIL_CONFIRM_PATH = "/users/confirmation";
    @Autowired
//    @Qualifier("hibernateUserDAO")
    @Qualifier("mongoUserDAO")
    private UserDAO userDAO;
    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;
    @Autowired
//    @Qualifier("hibernateVerificationTokenDAO")
    @Qualifier("mongoVerificationTokenDAO")
    private VerificationTokenDAO verificationTokenDAO;
    @Autowired
    private MailService mailService;

    @Override
    public User saveUser(User user) throws PersistenceException {
        if (user.getId() != 0) {
            user.setLogin(user.getLogin().toLowerCase());
            userDAO.update(user);
        } else {
            user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), null));
            user.setLogin(user.getLogin().toLowerCase());
            user = userDAO.create(user);
        }
        return user;
    }

    @Override
    public void deleteUser(long id) throws PersistenceException {
        userDAO.delete(id);
    }

    @Override
    public User findUserById(long id) throws PersistenceException {
        return userDAO.read(id);
    }

    @Override
    public List<User> getAllUsers() throws PersistenceException {
        return userDAO.readAll();
    }

    @Override
    public User getUserByLogin(String login) throws PersistenceException {
        return userDAO.getUserByLogin(login.toLowerCase());
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("user"));
        User user;
        try {
            user = getUserByLogin(login);
            if (user == null) {
                throw new UsernameNotFoundException("user not found");
            }
        } catch (PersistenceException e) {
            LOGGER.error(e);
            throw new UsernameNotFoundException("error reading user from DB");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roles);
    }


    @Override
    public void verifyEmail(User user, String host) throws PersistenceException {
        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(user.getLanguage().name()));
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);
        verificationToken.setDateExpire(calendar.getTime());
        verificationTokenDAO.create(verificationToken);
        String subject = messages.getString("mail.registration.subject");
        String textheader = messages.getString("mail.registration.textheader");
        String textfooter = messages.getString("mail.registration.textfooter");
        StringBuilder text = new StringBuilder();
        text.append(textheader).append("\n\n\n")
                .append(host).append(EMAIL_CONFIRM_PATH).append("?token=").append(token)
                .append("\n\n\n\n\n\n").append(textfooter);
        mailService.sendMail(user.getLogin(), subject, text.toString());
    }

    @Override
    public User verifyToken(String token) throws PersistenceException, TokenExpiredException {
        VerificationToken verificationToken = verificationTokenDAO.getByToken(token);
        if (verificationToken != null) {
            verificationTokenDAO.delete(verificationToken.getId());
            if (verificationToken.getDateExpire().getTime() < new Date().getTime()) {
                throw new TokenExpiredException();
            }
            User user = verificationToken.getUser();
            user.setActive(true);
            userDAO.update(user);
            return user;
        } else {
            return null;
        }
    }
}

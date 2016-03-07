package com.postman.validation;

import com.postman.PersistenceException;
import com.postman.User;
import com.postman.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public abstract class UserValidator extends GeneralAbstractValidator {
    protected static final Logger LOGGER = LogManager.getLogger(UserValidator.class);
    @Autowired
    private UserService userService;

    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    protected void checkUserInDataBase(Errors errors, User user){
        try {
            User userFromDB = userService.getUserByLogin(user.getLogin());
            if(userFromDB!=null&&user.getId()!=userFromDB.getId()){
                errors.rejectValue("login", "invalid.loginexists");
            }
        } catch (PersistenceException e) {
            errors.rejectValue("login", "dberror");
            LOGGER.error(e);
        }
    }
}

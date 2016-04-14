package com.postman.validation;

import com.postman.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
public class AddUserValidator extends UserValidator {

    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "empty.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "empty.confirmpassword");
        if (!errors.hasFieldErrors("login") && !validateEmail(user.getLogin())) {
            errors.rejectValue("login", "invalid.email");
        }
        if (!errors.hasFieldErrors("login")) {
            checkUserInDataBase(errors, user);
        }
        if (!errors.hasFieldErrors("password") && !validatePassword(user.getPassword())) {
            errors.rejectValue("password", "invalid.password");
        }
        if (!errors.hasFieldErrors("password") && !errors.hasFieldErrors("confirmpassword") && !user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "invalid.confirmpassword");
        }
    }

}

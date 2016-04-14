package com.postman.validation;

import com.postman.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
public class EditUserValidation extends UserValidator {

    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (user.getPassword() != null && !"".equals(user.getPassword())) {
            if (!validatePassword(user.getPassword())) {
                errors.rejectValue("password", "invalid.password");
            }
            if (!errors.hasFieldErrors("password") && !errors.hasFieldErrors("confirmpassword") && !user.getPassword().equals(user.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "invalid.confirmpassword");
            }
        }
    }

}

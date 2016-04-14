package com.postman.validation;

import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public abstract class GeneralAbstractValidator implements Validator {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z]).{4,12}$";

    protected boolean validateEmail(String value) {
        return validate(EMAIL_PATTERN, value);
    }

    protected boolean validatePassword(String value) {
        return validate(PASSWORD_PATTERN, value);
    }

    private boolean validate(String pattern, String value) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
    }
}

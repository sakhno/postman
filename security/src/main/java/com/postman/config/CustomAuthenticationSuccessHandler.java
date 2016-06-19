package com.postman.config;

import com.postman.PersistenceException;
import com.postman.UserService;
import com.postman.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Logger LOGGER = LogManager.getLogger(CustomAuthenticationSuccessHandler.class);
    @Autowired
    private UserService userService;
    @Resource
    private LocaleResolver localeResolver;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        try {
            User user = userService.getUserByLogin(userDetails.getUsername());
            if (user != null && user.getLanguage() != null) {
                localeResolver.setLocale(request, response, new Locale(user.getLanguage().name()));
            }
        } catch (PersistenceException e) {
            LOGGER.debug(e);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

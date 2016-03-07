package com.postman.controllers;

import com.postman.PersistenceException;
import com.postman.User;
import com.postman.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
public class MainPageController {
    private static final Logger LOGGER = LogManager.getLogger(MainPageController.class);

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String mainPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!"anonymousUser".equals(auth.getName())){
            try {
                User user = userService.getUserByLogin(auth.getName());
                model.addAttribute("user", user);
//                model.addAttribute("language", user.getLanguage());
            } catch (PersistenceException e) {
                LOGGER.error(e);
            }
        }
        return "main";
    }
}

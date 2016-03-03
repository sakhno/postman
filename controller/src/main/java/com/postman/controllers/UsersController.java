package com.postman.controllers;

import com.postman.Language;
import com.postman.PersistenceException;
import com.postman.User;
import com.postman.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
@RequestMapping(value = "/users")
public class UsersController {
    private static final Logger LOGGER = LogManager.getLogger(UsersController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String registrationPage(Model model){
        model.addAttribute("languages", Language.values());
        model.addAttribute("userForm", new User().setNotifyByEmail(true));
        return "registration";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String userSaveOrUpdate(@ModelAttribute("userForm") @Validated User user,
                                   BindingResult bindingResult, Model model){
        model.addAttribute("languages", Language.values());
        if(bindingResult.hasErrors()){
            return "registration";
        }else {
            try {
                userService.saveUser(user);
            } catch (PersistenceException e) {
                LOGGER.error(e);
                bindingResult.rejectValue("login", "dberror");
                return "registration";
            }
            return "redirect:/home";
        }
    }
}

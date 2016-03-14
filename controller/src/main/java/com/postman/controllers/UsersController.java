package com.postman.controllers;

import com.postman.*;
import com.postman.validation.AddUserValidator;
import com.postman.validation.EditUserValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
@RequestMapping(value = "/users")
public class UsersController {
    private static final Logger LOGGER = LogManager.getLogger(UsersController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;
    @Autowired
    private AddUserValidator addUserValidator;
    @Autowired
    private EditUserValidation editUserValidation;
    @Autowired
    protected AuthenticationManager authenticationManager;

    @InitBinder("userForm")
    protected void initUserFormBinder(WebDataBinder binder){
        binder.setValidator(addUserValidator);
    }

    @InitBinder("userEditForm")
    protected void initUserEditFormBinder(WebDataBinder binder){
        binder.setValidator(editUserValidation);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String registrationPage(Model model){
        model.addAttribute("languages", Language.values());
        model.addAttribute("userForm", new User().setNotifyByEmail(true));
        return "userform";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPage(Model model){
        model.addAttribute("languages", Language.values());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userService.getUserByLogin(auth.getName());
            model.addAttribute("userEditForm", user);
        } catch (PersistenceException e) {
            LOGGER.error(e);
        }
        return "userform";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String userRegistration(@ModelAttribute("userForm") @Validated User user, BindingResult bindingResult, Model model){
        model.addAttribute("languages", Language.values());
        if(bindingResult.hasErrors()){
            return "userform";
        }else {
            try {
                user = userService.saveUser(user);
                userService.verifyEmail(user);
            } catch (PersistenceException e) {
                LOGGER.error(e);
                bindingResult.rejectValue("login", "dberror");
                return "userform";
            }
            return "emailconfirmation";
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String userEditing(@ModelAttribute("userEditForm") @Validated User user,
                              BindingResult bindingResult, Model model){
        if(user.getPassword()==null||"".equals(user.getPassword())){
            User oldUser;
            try {
                oldUser = userService.findUserById(user.getId());
            } catch (PersistenceException e) {
                LOGGER.error(e);
                bindingResult.rejectValue("login", "dberror");
                return "userform";
            }
            user.setPassword(oldUser.getPassword());
        }else {
            user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), null));
        }
        return userSaveOrUpdate(user, bindingResult, model);
    }

    @RequestMapping(value = "/confirmation", method = RequestMethod.GET)
    public String emailConfirmation(Model model, @RequestParam("token") String token, HttpServletRequest request){
        try {
            User user = userService.verifyToken(token);
            if(user!=null){
                authenticateUserAndSetSession(user, request);
                model.addAttribute("message", "emailconfirmationsuccessful");
            }else {
                model.addAttribute("message", "confirmationerror");
            }
        } catch (PersistenceException e) {
            LOGGER.error(e);
            model.addAttribute("message", "dberror");
        } catch (TokenExpiredException e) {
            model.addAttribute("message", "tokenexpired");
        }
        return "emailconfirmation";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/confirmemail")
    @ResponseBody
    public boolean confirmemail(@RequestParam Map<String, String> parameters){
        long id = Long.parseLong(parameters.get("userid"));
        try {
            User user = userService.findUserById(id);
            userService.verifyEmail(user);
            return true;
        } catch (PersistenceException e) {
            LOGGER.error(e);
            return false;
        }
    }

    private String userSaveOrUpdate(User user, BindingResult bindingResult, Model model){
        model.addAttribute("languages", Language.values());
        if(bindingResult.hasErrors()){
            return "userform";
        }else {
            try {
                userService.saveUser(user);
            } catch (PersistenceException e) {
                LOGGER.error(e);
                bindingResult.rejectValue("login", "dberror");
                return "userform";
            }
            return "redirect:/home";
        }
    }

    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        UserDetails userDetails = userService.loadUserByUsername(user.getLogin());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}

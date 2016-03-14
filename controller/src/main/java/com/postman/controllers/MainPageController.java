package com.postman.controllers;

import com.postman.*;
import com.postman.validation.SearchForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
public class MainPageController {
    private static final Logger LOGGER = LogManager.getLogger(MainPageController.class);
    @Autowired
    UserService userService;
    @Autowired
    TrackService trackService;

    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String mainPage(Model model){
        model.addAttribute("user", getCurrentUser());
        model.addAttribute("searchForm", new SearchForm());
        return "main";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String findTrack(Model model, @ModelAttribute("searchForm") @Validated SearchForm searchForm){
        User user = getCurrentUser();
        model.addAttribute("user", user);
        String trackNumber = searchForm.getTrackNumber();
        if(trackNumber!=null&&!"".equals(trackNumber)){
            try {
                Track track = trackService.getTrack(trackNumber, user);
                model.addAttribute("track", track);
                return "main";
            } catch (PersistenceException e) {
                LOGGER.error(e);
                return "main";
            } catch (TrackNotFoundException e) {
                return "redirect:/home?trackerror";
            }
        }
        return "redirect:/home?trackerror";
    }

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if(!"anonymousUser".equals(auth.getName())){
            try {
                user = userService.getUserByLogin(auth.getName());
                user.setTracks(trackService.getAllUserTracks(user));
            } catch (PersistenceException e) {
                LOGGER.error(e);
            }
        }
        return user;
    }
}

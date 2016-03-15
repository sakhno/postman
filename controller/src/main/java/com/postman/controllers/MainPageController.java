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

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    MailService mailService;

    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String homePage(Model model, HttpServletRequest request){
        notifyMyAboutVisitor(request);
        model.addAttribute("user", getCurrentUser());
        model.addAttribute("searchForm", new SearchForm());
        return "home";
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
                return "home";
            } catch (PersistenceException e) {
                LOGGER.error(e);
                return "home";
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

    private void notifyMyAboutVisitor(HttpServletRequest request){
        String ip = request.getRemoteAddr();
        LOGGER.debug(ip);
        if("176.104.50.246".equals(ip)){
            return;
        }
        String server = request.getServerName();
        String toAddress = "sakhno83@gmail.com";
        String subject = "your site has been visited";
        mailService.sendMail(toAddress, subject, "Your site on "+server+" has been visited by "+ip);
    }
}

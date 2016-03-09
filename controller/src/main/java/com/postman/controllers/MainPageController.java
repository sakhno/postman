package com.postman.controllers;

import com.postman.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    TrackingService trackingService;

    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String mainPage(Model model){
        setUserToModel(model);
        return "main";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String findTrack(Model model, @RequestParam Map<String, String> params){
        setUserToModel(model);
        String trackNumber = params.get("newtrack");
        if(trackNumber!=null&&!"".equals(trackNumber)){
            try{
                PostService postService = trackingService.getPostService(trackNumber);
                LOGGER.debug(postService);
                if(trackingService.addSingleTrack(trackNumber, postService)){
                    Track track = trackingService.getSingleTrack(trackNumber);
                    model.addAttribute("track", track);
                    return "main";
                }
            }catch (TrackNotFoundException e) {
                return "redirect:/home?trackerror";
            }
        }
        return "redirect:/home?trackerror";
    }

    private void setUserToModel(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!"anonymousUser".equals(auth.getName())){
            try {
                User user = userService.getUserByLogin(auth.getName());
                model.addAttribute("user", user);
            } catch (PersistenceException e) {
                LOGGER.error(e);
            }
        }
    }
}

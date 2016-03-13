package com.postman.controllers;

import com.postman.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
@RequestMapping("/track")
public class TrackController {
    private static final Logger LOGGER = LogManager.getLogger(TrackController.class);
    @Autowired
    private TrackService trackService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addTrack(@RequestParam Map<String, String> parameters){
        try {
            User user = userService.findUserById(Long.parseLong(parameters.get("user")));
            Track track = trackService.findTrackById(Long.parseLong(parameters.get("track")));
            track.setUser(user);
            track.setActive(true);
            if(trackService.checkIfTrackExists(track)){
                return "exists";
            }else {
                trackService.saveTrack(track);
                return "success";
            }
        } catch (PersistenceException e) {
            LOGGER.error(e);
            return "dberror";
        }
    }
}

package com.postman.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
@RequestMapping("/tracks")
public class ManageTracksController {

    @RequestMapping(method = RequestMethod.GET)
    public String manageTracks(){
        return "tracks";
    }
}

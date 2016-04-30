package com.postman.controllers;

import com.postman.*;
import com.postman.model.Track;
import com.postman.model.User;
import com.postman.validation.SearchForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Locale;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
public class MainPageController {
    private static final Logger LOGGER = LogManager.getLogger(MainPageController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private TrackService trackService;
    @Autowired
    private MailService mailService;
    @Autowired
    private TranslationService translationService;

    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String homePage(Model model, HttpServletRequest request, Principal principal) {
        notifyMyAboutVisitor(request);
        model.addAttribute("user", getCurrentUser(principal));
        model.addAttribute("searchForm", new SearchForm());
        return "home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String findTrack(Model model, @ModelAttribute("searchForm") @Validated SearchForm searchForm, Principal principal) {
        User user = getCurrentUser(principal);
        model.addAttribute("user", user);
        String trackNumber = searchForm.getTrackNumber();
        if (trackNumber != null && !"".equals(trackNumber)) {
            try {
                Track track = trackService.getTrack(trackNumber, user);
                translateMessages(track);
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

    @RequestMapping(value = "/commingsoon")
    public String commingSoon() {
        return "commingsoon";
    }

    private User getCurrentUser(Principal principal) {
        if(principal!=null){
            User user = null;
            try {
                user = userService.getUserByLogin(principal.getName());
                user.setTracks(trackService.getAllUserTracks(user));
            } catch (PersistenceException e) {
                LOGGER.error(e);
            }
            return user;
        }else {
            return null;
        }
    }

    //translates messages of track to current session locale language
    private void translateMessages(Track track){
        Locale locale = LocaleContextHolder.getLocale();
        try {
            track.setMessages(translationService.translate(track.getMessages(), locale));
        } catch (Exception e) {
            LOGGER.error("translation failed", e);
        }
    }

    private void notifyMyAboutVisitor(HttpServletRequest request) {
        String server = request.getServerName();
        String ip = "";
        try {
            ip = request.getHeader("X-Forwarded-For").split(",")[0];
        } catch (Exception ignored) {
        }
        String toAddress = "sakhno83@gmail.com";
        String subject = "your site has been visited";
        if ("176.104.50.246".equals(ip) || "localhost".equals(server)) {
            return;
        }
        mailService.sendMail(toAddress, subject, "Your site " + server + " has been visited by " + ip);
    }
}

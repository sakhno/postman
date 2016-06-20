package com.postman.gwt.server;

import com.postman.PersistenceException;
import com.postman.TrackService;
import com.postman.UserService;
import com.postman.gwt.client.TracksManageService;
import com.postman.gwt.dto.TrackDTO;
import com.postman.model.Track;
import com.postman.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.Mapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
//@Service
public class TracksManageServiceImpl implements TracksManageService {
    private static final Logger LOGGER = LogManager.getLogger(TracksManageServiceImpl.class);
    @Autowired
    private TrackService trackService;
    @Autowired
    private UserService userService;
    @Autowired
    private DozerBeanMapperFactoryBean dozerBean;

    @Override
    public ArrayList<TrackDTO> getCurrentUserTracks() {
        ArrayList<TrackDTO> result = new ArrayList<>();
        try {
            Mapper mapper = dozerBean.getObject();
            ArrayList<Track> tracks = (ArrayList) trackService.getAllUserTracks(getCurrentUser());
            for (Track track : tracks) {
                result.add(mapper.map(track, TrackDTO.class));
            }
            return result;
        } catch (PersistenceException e) {
            LOGGER.error(e);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return result;
    }

    @Override
    public String test() {
        return "GWT RPC is working!";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (!"anonymousUser".equals(auth.getName())) {
            try {
                user = userService.getUserByLogin(auth.getName());
            } catch (PersistenceException e) {
                LOGGER.error(e);
            }
        }
        return user;
    }
}

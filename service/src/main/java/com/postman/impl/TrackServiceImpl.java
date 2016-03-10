package com.postman.impl;

import com.postman.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
@Transactional
public class TrackServiceImpl implements TrackService {
    private static final Logger LOGGER = LogManager.getLogger(TrackServiceImpl.class);
    @Autowired
    TrackDAO trackDAO;
    @Autowired
    PostServiceDAO postServiceDAO;

    @Override
    public Track saveTrack(Track track) throws PersistenceException {
        checkPostServicesInDB(track);
        if(track.getId()!=0){
            trackDAO.update(track);
            return track;
        }else if(checkIfTrackExists(track)){
            return trackDAO.getTrackByNumberAndUser(track);
        }else {
            return trackDAO.create(track);
        }

    }

    @Override
    public void deleteTrack(long id) throws PersistenceException {
        trackDAO.delete(id);
    }

    @Override
    public List<Track> getAllUserTracks(User user) throws PersistenceException {
        return null;
    }

    @Override
    public boolean checkIfTrackExists(Track track) throws PersistenceException {
        if(trackDAO.getTrackByNumberAndUser(track)!=null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Track findTrackById(long id) throws PersistenceException {
        return trackDAO.read(id);
    }

    private void checkPostServicesInDB(Track track){
        Set<PostService> services = new HashSet<>();
        for(PostService service: track.getServices()){
            services.add(findPostServiceInDB(service));
        }
        track.setServices(services);
    }

    private PostService findPostServiceInDB(PostService postService){
        PostService ps = postServiceDAO.getPostServiceByCode(postService.getCode());
        if(ps!=null){
            return ps;
        }else {
            return postService;
        }
    }
}

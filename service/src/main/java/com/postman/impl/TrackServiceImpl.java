package com.postman.impl;

import com.postman.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

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
    @Autowired
    TrackingService trackingService;
    @Autowired
    MessageDAO messageDAO;

    @Override
    public Track saveTrack(Track track) throws PersistenceException {
        checkPostServicesInDB(track);
        if(track.getId()!=0){
            trackDAO.update(track);
        }else if(checkIfTrackExists(track)){
            Track trackFromDB = trackDAO.getTrackByNumberAndUser(track);
            addNewMessages(trackFromDB, track);
            trackDAO.update(trackFromDB);
            return trackFromDB;
        }else {
            return trackDAO.create(track);
        }
        return track;
    }

    @Override
    public void deleteTrack(long id) throws PersistenceException {
        trackDAO.delete(id);
    }

    @Override
    public List<Track> getAllUserTracks(User user) throws PersistenceException {
        return trackDAO.getAllUserTracks(user);
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

    @Override
    public List<Track> findAllActiveTracks() throws PersistenceException {
        return trackDAO.getAllActiveTracks();
    }

    @Override
    public void updateAllActiveTracks() throws PersistenceException {
        Map<String, Track> updatedTracks = trackingService.getAllTracks();
        List<Track> activeTracks = trackDAO.getAllActiveTracks();
        for(Track track: activeTracks){
            Track trackFromApi = updatedTracks.get(track.getNumber());
            if(trackFromApi==null){
                continue;
            }
            addNewMessages(track, trackFromApi);
            saveTrack(track);
            LOGGER.debug("Track "+track.getNumber()+" and user "+track.getUser().getLogin()+" updated!");
        }
    }

    private void checkPostServicesInDB(Track track) throws PersistenceException {
        track.setOriginPostService(findPostServiceInDB(track.getOriginPostService()));
        track.setDestinationPostService(findPostServiceInDB(track.getDestinationPostService()));
    }

    private PostService findPostServiceInDB(PostService postService) throws PersistenceException {
        if(postService==null){
            return null;
        }
        PostService ps = postServiceDAO.getPostServiceByCode(postService.getCode());
        if(ps!=null){
            return ps;
        }else {
            return postServiceDAO.create(postService);
        }
    }

    private void addNewMessages(Track trackInDB, Track trackFromApi)throws PersistenceException{
        List<Message> messages = trackInDB.getMessages();
        for(Message message: messages){
            LOGGER.debug(message);
        }
        for(Message message:trackFromApi.getMessages()){
            LOGGER.debug(message);
            if(!messages.contains(message)){
                message.setTrack(trackInDB);
                messageDAO.create(message);
                messages.add(message);
            }
        }
    }
}

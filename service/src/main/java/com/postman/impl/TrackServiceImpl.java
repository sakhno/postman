package com.postman.impl;

import com.postman.*;
import com.postman.model.Message;
import com.postman.model.PostService;
import com.postman.model.Track;
import com.postman.model.User;
import com.postman.model.exception.ServiceException;
import com.postman.model.exception.TrackAllreadyExistsException;
import com.postman.model.exception.TrackNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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
//    @Qualifier("hibernateTrackDAO")
    @Qualifier("mongoTrackDAO")
    private TrackDAO trackDAO;
    @Autowired
//    @Qualifier("hibernatePostServiceDAO")
    @Qualifier("mongoPostServiceDAO")
    private PostServiceDAO postServiceDAO;
    @Autowired
    private TrackingService trackingService;
    @Autowired
//    @Qualifier("hibernateMessageDAO")
    @Qualifier("mongoMessageDAO")
    private MessageDAO messageDAO;
    @Autowired
    private MailService mailService;

    @Override
    public Track saveTrack(Track track) throws PersistenceException {
        checkPostServicesInDB(track);
        if (track.getId() != 0) {
            trackDAO.update(track);
        } else if (checkIfTrackExists(track)) {
            Track trackFromDB = trackDAO.getTrackByNumberAndUser(track);
            addNewMessages(trackFromDB, track, null);
            trackDAO.update(trackFromDB);
            return trackFromDB;
        } else {
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
        if (trackDAO.getTrackByNumberAndUser(track) != null) {
            return true;
        } else {
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
    public void updateAllActiveTracks() throws ServiceException {
        Map<User, List<Message>> notifyUserMap = new HashMap<>();
        Map<String, Track> updatedTracks = trackingService.getAllTracks();
        List<Track> activeTracks = trackDAO.getAllActiveTracks();
        for (Track track : activeTracks) {
            Track trackFromApi = updatedTracks.get(track.getNumber());
            if (trackFromApi == null) {
                LOGGER.error(track.getNumber() + " not found by update all method.");
                continue;
            }
            if (track.getUser() != null && track.getUser().isNotifyByEmail()) {
                addNewMessages(track, trackFromApi, notifyUserMap);
            } else {
                addNewMessages(track, trackFromApi, null);
            }
            saveTrack(track);
        }
        mailService.notifyStatuses(notifyUserMap);
    }

    @Override
    public Track getTrackByNumberAndUser(String trackNumber, User user) throws PersistenceException {//TODO check if needed, refactor
        Track track = new Track();
        track.setNumber(trackNumber);
        track.setUser(user);
        return trackDAO.getTrackByNumberAndUser(track);
    }

    @Override
    public Track getTrack(String trackNumber, User currentUser) throws ServiceException {
        Track track = getTrackByNumberAndUser(trackNumber, currentUser);
        if (track == null) {
            track = getTrackByNumberAndUser(trackNumber, null);
        }
        if (track != null) {
            Track updatedTrack;
            try {
                updatedTrack = trackingService.getSingleTrackById(track.getAftershipId());
            } catch (TrackNotFoundException e) {
                LOGGER.error(e);
                updatedTrack = trackingService.addSingleTrack(track.getNumber());
            }
            addNewMessages(track, updatedTrack, null);
            saveTrack(track);
        } else {
            track = addNewTrackToApi(trackNumber);
            try {
                track.setUser(currentUser);
                track = saveTrack(track);
            } catch (PersistenceException e) {
                LOGGER.error(e);
                trackingService.deleteTrack(track.getAftershipId());
            }
        }
        return track;
    }

    private Track addNewTrackToApi(String trackNumber) throws ServiceException {
        Track createdTrack;
        try {
            createdTrack = trackingService.addSingleTrack(trackNumber);
        } catch (TrackAllreadyExistsException e) {
            createdTrack = trackingService.getAllTracks().get(trackNumber);
//            createdTrack = trackingService.getSingleTrack(trackNumber);
        }
        return createdTrack;
    }

    private void checkPostServicesInDB(Track track) throws PersistenceException {
        track.setOriginPostService(findPostServiceInDB(track.getOriginPostService()));
        track.setDestinationPostService(findPostServiceInDB(track.getDestinationPostService()));
    }

    private PostService findPostServiceInDB(PostService postService) throws PersistenceException {
        if (postService == null) {
            return null;
        }
        PostService ps = postServiceDAO.getPostServiceByCode(postService.getCode());
        if (ps != null) {
            return ps;
        } else {
            return postServiceDAO.create(postService);
        }
    }

    private void addNewMessages(Track trackInDB, Track trackFromApi, Map<User, List<Message>> notifyUserMap) throws PersistenceException {
        List<Message> messages = trackInDB.getMessages();
        if(trackFromApi!= null) {
            for (Message message : trackFromApi.getMessages()) {
                if (!messages.contains(message)) {
                    message.setTrack(trackInDB);
                    messageDAO.create(message);
                    messages.add(message);
                    addMesageToNotifyMap(notifyUserMap, trackInDB.getUser(), message);
                }
            }
        }
    }

    private void addMesageToNotifyMap(Map<User, List<Message>> notifyUserMap, User user, Message message) {
        if (notifyUserMap != null && user.isActive() && user.isNotifyByEmail()) {
            if (!notifyUserMap.containsKey(user)) {
                notifyUserMap.put(user, new ArrayList<Message>());
            }
            notifyUserMap.get(user).add(message);
        }
    }

    @Override
    public int getNumberOfUnreadMessages(User user) throws PersistenceException {
        return messageDAO.getNumberOfUnreadMessages(user);
    }

    @Override
    public int getNumberOfUnreadMessages(Track track) throws PersistenceException {
        return messageDAO.getNumberOfUnreadMessages(track);
    }
}

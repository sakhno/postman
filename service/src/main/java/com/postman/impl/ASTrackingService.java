package com.postman.impl;

import com.postman.TrackingService;
import com.postman.aftership.ASTrackingRepository;
import com.postman.aftership.entity.*;
import com.postman.model.Message;
import com.postman.model.PostService;
import com.postman.model.Track;
import com.postman.model.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by antonsakhno on 19.03.17.
 */
@Service
public class ASTrackingService implements TrackingService {

    @Autowired
    private ASTrackingRepository asTrackingRepository;

    @Override
    public PostService getPostService(String trackCode) throws ServiceException {
        return null;
    }

    @Override
    public Track getSingleTrack(String trackCode, PostService postService) throws ServiceException {
        return convertToTrack(asTrackingRepository.getTrackByNumber(postService.getCode(), trackCode).getData().getTracking());
    }

    @Override
    public Track addSingleTrack(String trackCode) throws ServiceException {
        ASSingleTrackResponce asSingleTrackResponce = asTrackingRepository
                .addTrack(trackCode);
        return convertToTrack(asSingleTrackResponce.getData().getTracking());
    }

    @Override
    public Track getSingleTrackById(String trackId) throws ServiceException {
        return convertToTrack(asTrackingRepository.getTrackById(trackId).getData().getTracking());
    }

    @Override
    public Track addSingleTrack(String trackCode, PostService postService) throws ServiceException {
        return addSingleTrack(trackCode);
    }

    @Override
    public Map<String, Track> getAllTracks()  throws ServiceException {
        ASMultiTrackResponce allTracks = asTrackingRepository.getAllTracks();
        Map<String, Track> result = new HashMap<>();
        allTracks.getData().getTrackings().parallelStream().forEach(t -> result.put(t.getTrackingNumber(), convertToTrack(t)));
        return result;
    }

    @Override
    public void deleteTrack(String id) throws ServiceException {
        asTrackingRepository.deleteTrack(id);
    }

    private Track convertToTrack(ASTracking asTracking) {
        return new Track()
                .setActive(asTracking.isActive())
                .setDateCreated(convertDate(asTracking.getCreatedAt()))
                .setMessages(convertToMessages(asTracking.getCheckpoints()))
                .setNumber(asTracking.getTrackingNumber())
                .setAftershipId(asTracking.getId());
    }

    private List<Message> convertToMessages(List<ASCheckpoint> checkpoints) {
        List<Message> messages = new ArrayList<>();
        checkpoints.forEach(c ->
                messages.add(new Message()
                        .setDate(convertDate(c.getCreatedAt()))
                        .setText(c.getMessage())
                ));
        return messages;
    }

    private Date convertDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}

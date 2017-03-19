package com.postman;

import com.postman.model.PostService;
import com.postman.model.Track;
import com.postman.model.exception.ServiceException;

import java.util.Map;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface TrackingService {
    PostService getPostService(String trackCode) throws ServiceException;

    Track getSingleTrack(String trackCode, PostService postService) throws ServiceException;

    Track addSingleTrack(String trackCode) throws ServiceException;

    Track getSingleTrackById(String trackId) throws ServiceException;

    Track addSingleTrack(String trackCode, PostService postService) throws ServiceException;

    Map<String, Track> getAllTracks() throws ServiceException;

    void deleteTrack(String id) throws ServiceException;
}

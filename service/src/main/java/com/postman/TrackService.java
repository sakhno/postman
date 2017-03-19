package com.postman;

import com.postman.model.Track;
import com.postman.model.User;
import com.postman.model.exception.ServiceException;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface TrackService {
    Track saveTrack(Track track) throws ServiceException;

    void deleteTrack(long id) throws ServiceException;

    List<Track> getAllUserTracks(User user) throws ServiceException;

    boolean checkIfTrackExists(Track track) throws ServiceException;

    Track findTrackById(long id) throws ServiceException;

    List<Track> findAllActiveTracks() throws ServiceException;

    void updateAllActiveTracks() throws ServiceException;

    Track getTrackByNumberAndUser(String trackNumber, User user) throws ServiceException;

    Track getTrack(String trackNumber, User currentUser) throws ServiceException;

    int getNumberOfUnreadMessages(User user) throws ServiceException;

    int getNumberOfUnreadMessages(Track track) throws ServiceException;
}

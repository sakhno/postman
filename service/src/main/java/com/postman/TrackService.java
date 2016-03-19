package com.postman;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface TrackService {
    Track saveTrack(Track track) throws PersistenceException;
    void deleteTrack(long id) throws PersistenceException;
    List<Track> getAllUserTracks(User user) throws PersistenceException;
    boolean checkIfTrackExists(Track track) throws PersistenceException;
    Track findTrackById(long id) throws PersistenceException;
    List<Track> findAllActiveTracks() throws PersistenceException;
    void updateAllActiveTracks() throws PersistenceException;
    Track getTrackByNumberAndUser(String trackNumber, User user) throws PersistenceException;
    Track getTrack(String trackNumber, User currentUser) throws PersistenceException, TrackNotFoundException;
    int getNumberOfUnreadMessages(User user) throws PersistenceException;
    int getNumberOfUnreadMessages(Track track) throws PersistenceException;
}

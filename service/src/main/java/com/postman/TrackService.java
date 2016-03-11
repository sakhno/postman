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
}

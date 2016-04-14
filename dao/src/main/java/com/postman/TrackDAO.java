package com.postman;

import com.postman.model.Track;
import com.postman.model.User;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface TrackDAO extends GenericDAO<Track> {
    Track getTrackByNumberAndUser(Track track);

    List<Track> getAllUserTracks(User user);

    List<Track> getAllActiveTracks();
}

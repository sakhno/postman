package com.postman;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface TrackingService {
    PostService getPostService(String trackCode) throws TrackNotFoundException;
    Track getSingleTrack(String trackCode, PostService postService) throws TrackNotFoundException;
    boolean addSingleTrack(String trackCode) throws TrackNotFoundException;
    Track getSingleTrack(String trackCode) throws TrackNotFoundException;
    boolean addSingleTrack(String trackCode, PostService postService) throws TrackNotFoundException;
}

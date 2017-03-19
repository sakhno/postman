package com.postman.aftership;

import com.postman.aftership.entity.ASMultiTrackResponce;
import com.postman.aftership.entity.ASSingleTrackResponce;
import com.postman.model.exception.ServiceException;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Created by antonsakhno on 19.02.17.
 */
public interface ASTrackingRepository {

    @RequestLine("POST /trackings")
    @Headers("Content-Type: application/json")
    @Body("%7B\"tracking\": %7B\"tracking_number\":\"{trackingNumber}\"%7D%7D")
    ASSingleTrackResponce addTrack(@Param("trackingNumber") String trackingNumber) throws ServiceException;

    @RequestLine("GET /trackings/{id}")
    ASSingleTrackResponce getTrackById(@Param("id") String trackId) throws ServiceException;

    @RequestLine("GET /trackings/{slug}/{number}")
    ASSingleTrackResponce getTrackByNumber(@Param("slug") String slug,
                                           @Param("number") String number) throws ServiceException;

    @RequestLine("GET /trackings")
    ASMultiTrackResponce getAllTracks() throws ServiceException;


    @RequestLine("DELETE /trackings/{id}")
    ASSingleTrackResponce deleteTrack(@Param("id") String trackId) throws ServiceException;

}

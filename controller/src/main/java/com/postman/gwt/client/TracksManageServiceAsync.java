package com.postman.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.postman.gwt.dto.TrackDTO;

import java.util.ArrayList;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface TracksManageServiceAsync {
    void getCurrentUserTracks(AsyncCallback<ArrayList<TrackDTO>> callback);

    void test(AsyncCallback<String> callback);
}
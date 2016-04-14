package com.postman.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.postman.gwt.dto.TrackDTO;

import java.util.ArrayList;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */

@RemoteServiceRelativePath("postmanGwtServices/tracksManageService")
public interface TracksManageService extends RemoteService {
    ArrayList<TrackDTO> getCurrentUserTracks();

    String test();
}

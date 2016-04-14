package com.postman.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.postman.gwt.dto.MessageDTO;
import com.postman.gwt.dto.TrackDTO;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class Postmangwt implements EntryPoint {
    private TracksManageServiceAsync tracksManageService = GWT.create(TracksManageService.class);

    @Override
    public void onModuleLoad() {
        Button newTrackButton = Button.wrap(DOM.getElementById("newtrackbutton"));
        newTrackButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Window.alert("new track adding");
            }
        });

        final CellTable<TrackDTO> activeTracks = new CellTable();
        activeTracks.setStyleName("table table-striped table-hover");

        TextColumn<TrackDTO> dateColumn = new TextColumn<TrackDTO>() {
            @Override
            public String getValue(TrackDTO track) {
                Date createDate = track.getDateCreated();
                DateTimeFormat format = DateTimeFormat.getFormat("dd.MM.yyyy");
                return format.format(createDate);
            }
        };

        TextColumn<TrackDTO> trackNumberColumn = new TextColumn<TrackDTO>() {
            @Override
            public String getValue(TrackDTO track) {
                return track.getNumber();
            }
        };

        TextColumn<TrackDTO> trackNameColumn = new TextColumn<TrackDTO>() {
            @Override
            public String getValue(TrackDTO track) {
                return track.getName();
            }
        };

        TextColumn<TrackDTO> newMessageColumn = new TextColumn<TrackDTO>() {
            @Override
            public String getValue(TrackDTO track) {
                return String.valueOf(getNumberOfNewMessages(track));
            }
        };
        activeTracks.addColumn(dateColumn);
        activeTracks.addColumn(trackNumberColumn);
        activeTracks.addColumn(trackNameColumn);
        activeTracks.addColumn(newMessageColumn);

        AsyncCallback<ArrayList<TrackDTO>> callback = new AsyncCallback<ArrayList<TrackDTO>>() {
            @Override
            public void onFailure(Throwable throwable) {
            }

            @Override
            public void onSuccess(ArrayList<TrackDTO> tracks) {
                activeTracks.setRowData(tracks);
            }
        };
        tracksManageService.getCurrentUserTracks(callback);
        RootPanel.get("activetrackspanel").add(activeTracks);
        test();
    }

    private void test() {
        final Label label = new Label();
        AsyncCallback<String> testCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(String result) {
                label.setText(result);
            }
        };
        tracksManageService.test(testCallback);
        RootPanel.get("trackinfobody").add(label);
    }

    private int getNumberOfNewMessages(TrackDTO track) {
        ArrayList<MessageDTO> messages = (ArrayList) track.getMessages();
        int count = 0;
        if (messages == null) {
            return count;
        }
        for (MessageDTO message : messages) {
            if (!message.isReaded()) {
                count++;
            }
        }
        return count;
    }
}

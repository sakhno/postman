package com.postman.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class Postmangwt implements EntryPoint {
    public void onModuleLoad() {
        Label testLabel = new Label("GWT is working!");
        RootPanel.get().add(testLabel);
    }
}

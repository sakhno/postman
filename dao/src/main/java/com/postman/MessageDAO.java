package com.postman;

import com.postman.model.Message;
import com.postman.model.Track;
import com.postman.model.User;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface MessageDAO extends GenericDAO<Message> {
    List<Message> readAll(String trackNumber, User user);

    int getNumberOfUnreadMessages(User user);

    int getNumberOfUnreadMessages(Track track);
}

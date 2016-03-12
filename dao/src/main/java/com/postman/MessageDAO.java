package com.postman;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface MessageDAO extends GenericDAO<Message> {
    List<Message> readAll(String trackNumber, User user);
}

package com.postman.gwt.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TrackDTO implements Serializable {
    private long id;
    private String number;
    private String name;
    //    private UserDTO user;
    private boolean active;
    //    private PostService originPostService;
//    private PostService destinationPostService;
    private Date dateCreated;
    private List<MessageDTO> messages;

    public long getId() {
        return id;
    }

    public TrackDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public TrackDTO setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public TrackDTO setName(String name) {
        this.name = name;
        return this;
    }

//    public UserDTO getUser() {
//        return user;
//    }
//
//    public TrackDTO setUser(UserDTO user) {
//        this.user = user;
//        return this;
//    }

    public boolean isActive() {
        return active;
    }

    public TrackDTO setActive(boolean active) {
        this.active = active;
        return this;
    }

//    public PostService getOriginPostService() {
//        return originPostService;
//    }
//
//    public TrackDTO setOriginPostService(PostService originPostService) {
//        this.originPostService = originPostService;
//        return this;
//    }
//
//    public PostService getDestinationPostService() {
//        return destinationPostService;
//    }
//
//    public TrackDTO setDestinationPostService(PostService destinationPostService) {
//        this.destinationPostService = destinationPostService;
//        return this;
//    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public TrackDTO setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public TrackDTO setMessages(List<MessageDTO> messages) {
        this.messages = messages;
        return this;
    }
}

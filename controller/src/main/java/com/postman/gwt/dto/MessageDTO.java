package com.postman.gwt.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class MessageDTO implements Serializable {
    private long id;
    private String text;
    private TrackDTO track;
    private boolean readed;
    private Date date;

    public long getId() {
        return id;
    }

    public MessageDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public MessageDTO setText(String text) {
        this.text = text;
        return this;
    }

    public TrackDTO getTrack() {
        return track;
    }

    public MessageDTO setTrack(TrackDTO track) {
        this.track = track;
        return this;
    }

    public boolean isReaded() {
        return readed;
    }

    public MessageDTO setReaded(boolean readed) {
        this.readed = readed;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public MessageDTO setDate(Date date) {
        this.date = date;
        return this;
    }
}

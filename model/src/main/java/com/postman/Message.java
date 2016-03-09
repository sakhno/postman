package com.postman;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Entity
@Table(name = "messages")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    @ManyToOne
    private Track track;
    private boolean readed;
    private Date date;

    public long getId() {
        return id;
    }

    public Message setId(long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public Track getTrack() {
        return track;
    }

    public Message setTrack(Track track) {
        this.track = track;
        return this;
    }

    public boolean isReaded() {
        return readed;
    }

    public Message setReaded(boolean readed) {
        this.readed = readed;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Message setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", readed=" + readed +
                ", date=" + date +
                '}';
    }
}

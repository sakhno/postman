package com.postman;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String login;
    private String name;
    private String password;
    @Transient
    private String confirmPassword;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Track> tracks;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Message> messages;
    private boolean notifyByEmail;
    @Enumerated(EnumType.ORDINAL)
    private Language language;

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public User setTracks(List<Track> tracks) {
        this.tracks = tracks;
        return this;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public User setMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    public boolean isNotifyByEmail() {
        return notifyByEmail;
    }

    public User setNotifyByEmail(boolean notifyByEmail) {
        this.notifyByEmail = notifyByEmail;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public User setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public Language getLanguage() {
        return language;
    }

    public User setLanguage(Language language) {
        this.language = language;
        return this;
    }
}

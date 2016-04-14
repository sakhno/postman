package com.postman.gwt.dto;

import java.io.Serializable;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class UserDTO implements Serializable {
    private long id;
    private String login;
    private String name;
    private boolean notifyByEmail;
    private boolean active;

    public long getId() {
        return id;
    }

    public UserDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public UserDTO setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDTO setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isNotifyByEmail() {
        return notifyByEmail;
    }

    public UserDTO setNotifyByEmail(boolean notifyByEmail) {
        this.notifyByEmail = notifyByEmail;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public UserDTO setActive(boolean active) {
        this.active = active;
        return this;
    }
}

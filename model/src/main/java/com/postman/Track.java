package com.postman;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class Track implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String number;
    private String name;
    @OneToOne
    private User user;
    private boolean active;
    @Enumerated(EnumType.ORDINAL)
    private Set<PostService> services;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateCreated;

    @PrePersist
    private void onCreate(){
        dateCreated = new Date();
    }

    public long getId() {
        return id;
    }

    public Track setId(long id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public Track setNumber(String number) {
        this.number = number;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Track setActive(boolean active) {
        this.active = active;
        return this;
    }

    public Set<PostService> getServices() {
        return services;
    }

    public Track setServices(Set<PostService> services) {
        this.services = services;
        return this;
    }

    public String getName() {
        return name;
    }

    public Track setName(String name) {
        this.name = name;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Track setUser(User user) {
        this.user = user;
        return this;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Track setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }
}

package com.postman;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Entity
@Table(name = "track")
public class Track implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String number;
    private String name;
    @ManyToOne
    private User user;
    private boolean active;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "postservice_id"))
    private Set<PostService> services;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateCreated;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "track")
    private List<Message> messages;

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

    public List<Message> getMessages() {
        return messages;
    }

    public Track setMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", user=" + user +
                ", active=" + active +
                ", services=" + services +
                ", dateCreated=" + dateCreated +
                ", messages=" + messages +
                '}';
    }
}

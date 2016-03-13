package com.postman;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @ManyToOne
    private PostService originPostService;
    @ManyToOne
    private PostService destinationPostService;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateCreated;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "track")
    @OrderBy(value = "date desc")
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

    public PostService getOriginPostService() {
        return originPostService;
    }

    public Track setOriginPostService(PostService originPostService) {
        this.originPostService = originPostService;
        return this;
    }

    public PostService getDestinationPostService() {
        return destinationPostService;
    }

    public Track setDestinationPostService(PostService destinationPostService) {
        this.destinationPostService = destinationPostService;
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
                ", originPostService=" + originPostService +
                ", destinationPostService=" + destinationPostService +
                ", dateCreated=" + dateCreated +
                ", messages=" + messages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (id != track.id) return false;
        if (active != track.active) return false;
        if (!number.equals(track.number)) return false;
        if (name != null ? !name.equals(track.name) : track.name != null) return false;
        if (user != null ? !user.equals(track.user) : track.user != null) return false;
        if (originPostService != null ? !originPostService.equals(track.originPostService) : track.originPostService != null)
            return false;
        if (destinationPostService != null ? !destinationPostService.equals(track.destinationPostService) : track.destinationPostService != null)
            return false;
        return dateCreated.equals(track.dateCreated);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + number.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (originPostService != null ? originPostService.hashCode() : 0);
        result = 31 * result + (destinationPostService != null ? destinationPostService.hashCode() : 0);
        result = 31 * result + dateCreated.hashCode();
        return result;
    }
}

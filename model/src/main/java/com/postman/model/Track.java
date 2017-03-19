package com.postman.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "track")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "test")
public class Track implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String aftershipId;
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
    private void onCreate() {
        dateCreated = new Date();
    }
}

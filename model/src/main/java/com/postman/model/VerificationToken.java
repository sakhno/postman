package com.postman.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Entity
@Table(name = "verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    @OneToOne
    private User user;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateExpire;

    public String getToken() {
        return token;
    }

    public VerificationToken setToken(String token) {
        this.token = token;
        return this;
    }

    public User getUser() {
        return user;
    }

    public VerificationToken setUser(User user) {
        this.user = user;
        return this;
    }

    public long getId() {
        return id;
    }

    public VerificationToken setId(long id) {
        this.id = id;
        return this;
    }

    public Date getDateExpire() {
        return dateExpire;
    }

    public VerificationToken setDateExpire(Date dateExpire) {
        this.dateExpire = dateExpire;
        return this;
    }
}

package com.postman.model;

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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Track> tracks;
    private boolean notifyByEmail;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean active;
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

    public boolean isActive() {
        return active;
    }

    public User setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", tracks=" + tracks +
                ", notifyByEmail=" + notifyByEmail +
                ", active=" + active +
                ", language=" + language +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (notifyByEmail != user.notifyByEmail) return false;
        if (active != user.active) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (confirmPassword != null ? !confirmPassword.equals(user.confirmPassword) : user.confirmPassword != null)
            return false;
        if (tracks != null ? !tracks.equals(user.tracks) : user.tracks != null) return false;
        return language == user.language;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (confirmPassword != null ? confirmPassword.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        result = 31 * result + (notifyByEmail ? 1 : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }
}

package com.postman.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Entity
@Table(name = "postservice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "test")
public class PostService implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(unique = true)
    private String code;

    public long getId() {
        return id;
    }

    public PostService setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PostService setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PostService setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostService that = (PostService) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return code != null ? code.equals(that.code) : that.code == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PostService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

package com.postman;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TMMessage implements Serializable{
    @JsonProperty(value = "Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    @JsonProperty(value = "StatusDescription")
    private String statusDescription;
    @JsonProperty(value = "Details")
    private String details;

    public Date getDate() {
        return date;
    }

    public TMMessage setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public TMMessage setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public TMMessage setDetails(String details) {
        this.details = details;
        return this;
    }

    @Override
    public String toString() {
        return "TMMessage{" +
                "date=" + date +
                ", statusDescription='" + statusDescription + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}

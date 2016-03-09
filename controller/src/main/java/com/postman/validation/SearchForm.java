package com.postman.validation;

import java.io.Serializable;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class SearchForm implements Serializable {
    private String trackNumber;

    public String getTrackNumber() {
        return trackNumber;
    }

    public SearchForm setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
        return this;
    }
}

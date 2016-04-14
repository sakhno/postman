package com.postman;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMCommonRequest implements Serializable {
    private TMMeta meta;

    public TMMeta getMeta() {
        return meta;
    }

    public TMCommonRequest setMeta(TMMeta meta) {
        this.meta = meta;
        return this;
    }

    @Override
    public String toString() {
        return "TMCommonRequest{" +
                "meta=" + meta +
                '}';
    }
}

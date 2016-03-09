package com.postman;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TMSinglePostService implements Serializable{
    private TMMeta meta;
    @JsonProperty(value = "data")
    private TMCarrier[] carriers;

    public TMMeta getMeta() {
        return meta;
    }

    public TMSinglePostService setMeta(TMMeta meta) {
        this.meta = meta;
        return this;
    }

    public TMCarrier[] getCarriers() {
        return carriers;
    }

    public TMSinglePostService setCarriers(TMCarrier[] carriers) {
        this.carriers = carriers;
        return this;
    }
}

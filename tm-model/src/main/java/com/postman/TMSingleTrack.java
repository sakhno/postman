package com.postman;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TMSingleTrack {
    private TMMeta meta;
    @JsonProperty(value = "data")
    private TMTrack track;

    public TMMeta getMeta() {
        return meta;
    }

    public TMSingleTrack setMeta(TMMeta meta) {
        this.meta = meta;
        return this;
    }

    public TMTrack getTrack() {
        return track;
    }

    public TMSingleTrack setTrack(TMTrack track) {
        this.track = track;
        return this;
    }
}

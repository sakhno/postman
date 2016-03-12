package com.postman;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TMMultipleTracks {
    private TMMeta meta;
    private TMMultipleTrackData data;

    public TMMeta getMeta() {
        return meta;
    }

    public TMMultipleTracks setMeta(TMMeta meta) {
        this.meta = meta;
        return this;
    }

    public TMMultipleTrackData getData() {
        return data;
    }

    public TMMultipleTracks setData(TMMultipleTrackData data) {
        this.data = data;
        return this;
    }
}

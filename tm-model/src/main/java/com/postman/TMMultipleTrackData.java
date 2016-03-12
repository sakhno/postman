package com.postman;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TMMultipleTrackData {
    private int page;
    private int limit;
    private int total;
    private TMTrack[] items;

    public int getPage() {
        return page;
    }

    public TMMultipleTrackData setPage(int page) {
        this.page = page;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public TMMultipleTrackData setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public TMMultipleTrackData setTotal(int total) {
        this.total = total;
        return this;
    }

    public TMTrack[] getItems() {
        return items;
    }

    public TMMultipleTrackData setItems(TMTrack[] items) {
        this.items = items;
        return this;
    }
}

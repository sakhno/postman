package com.postman;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TrackNotFoundException extends Exception {
    public TrackNotFoundException() {
    }

    public TrackNotFoundException(String message) {
        super(message);
    }

    public TrackNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrackNotFoundException(Throwable cause) {
        super(cause);
    }

    public TrackNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

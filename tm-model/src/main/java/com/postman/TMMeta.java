package com.postman;

import java.io.Serializable;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TMMeta implements Serializable{
    private int code;
    private String type;
    private String message;

    public int getCode() {
        return code;
    }

    public TMMeta setCode(int code) {
        this.code = code;
        return this;
    }

    public String getType() {
        return type;
    }

    public TMMeta setType(String type) {
        this.type = type;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public TMMeta setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "TMMeta{" +
                "code=" + code +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

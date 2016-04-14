package com.postman;


import java.io.Serializable;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TMCarrier implements Serializable {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public TMCarrier setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public TMCarrier setCode(String code) {
        this.code = code;
        return this;
    }
}

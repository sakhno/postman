package com.postman;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Arrays;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TMTrackInfo {
    private URI weblink;
    private String phone;
    @JsonProperty(value = "carrier_code")
    private String carrierCode;
    private TMMessage[] trackinfo;

    public URI getWeblink() {
        return weblink;
    }

    public TMTrackInfo setWeblink(URI weblink) {
        this.weblink = weblink;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public TMTrackInfo setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public TMTrackInfo setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
        return this;
    }

    public TMMessage[] getTrackinfo() {
        return trackinfo;
    }

    public TMTrackInfo setTrackinfo(TMMessage[] trackinfo) {
        this.trackinfo = trackinfo;
        return this;
    }

    @Override
    public String toString() {
        return "TMTrackInfo{" +
                "weblink=" + weblink +
                ", phone='" + phone + '\'' +
                ", carrierCode='" + carrierCode + '\'' +
                ", trackinfo=" + Arrays.toString(trackinfo) +
                '}';
    }
}

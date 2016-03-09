package com.postman;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMTrack implements Serializable {
    private String id;
    @JsonProperty(value = "tracking_number")
    private String trackingNumber;
    @JsonProperty(value = "carrier_code")
    private String carrierCode;
    private String status;
    @JsonProperty(value = "created_at")
    private Date created;
    @JsonProperty(value = "updated_at")
    private Date updated;
    @JsonProperty(value = "original_country")
    private String originalCountry;
    private int itemTimeLength;
    @JsonProperty(value = "origin_info")
    private TMTrackInfo originInfo;
    @JsonProperty(value = "destination_info")
    private TMTrackInfo destinationInfo;

    public String getId() {
        return id;
    }

    public TMTrack setId(String id) {
        this.id = id;
        return this;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public TMTrack setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public TMTrack setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TMTrack setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public TMTrack setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Date getUpdated() {
        return updated;
    }

    public TMTrack setUpdated(Date updated) {
        this.updated = updated;
        return this;
    }

    public String getOriginalCountry() {
        return originalCountry;
    }

    public TMTrack setOriginalCountry(String originalCountry) {
        this.originalCountry = originalCountry;
        return this;
    }

    public int getItemTimeLength() {
        return itemTimeLength;
    }

    public TMTrack setItemTimeLength(int itemTimeLength) {
        this.itemTimeLength = itemTimeLength;
        return this;
    }

    public TMTrackInfo getOriginInfo() {
        return originInfo;
    }

    public TMTrack setOriginInfo(TMTrackInfo originInfo) {
        this.originInfo = originInfo;
        return this;
    }

    public TMTrackInfo getDestinationInfo() {
        return destinationInfo;
    }

    public TMTrack setDestinationInfo(TMTrackInfo destinationInfo) {
        this.destinationInfo = destinationInfo;
        return this;
    }

    @Override
    public String toString() {
        return "TMTrack{" +
                "id='" + id + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", carrierCode='" + carrierCode + '\'' +
                ", status='" + status + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", originalCountry='" + originalCountry + '\'' +
                ", itemTimeLength=" + itemTimeLength +
                ", originInfo=" + originInfo +
                ", destinationInfo=" + destinationInfo +
                '}';
    }
}

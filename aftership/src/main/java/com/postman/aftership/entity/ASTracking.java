package com.postman.aftership.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by antonsakhno on 19.02.17.
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ASTracking {
    private String id;
    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;
    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;
    @JsonProperty(value = "tracking_number")
    private String trackingNumber;
    @JsonProperty(value = "tracking_ship_date")
    private LocalDateTime shipDate;
    private String slug;
    private boolean active;
    @JsonProperty(value = "destination_country_iso3")
    private String destinationCountry;
    @JsonProperty(value = "origin_country_iso3")
    private String originalCountry;
    @JsonProperty(value = "shipment_type")
    private String shipmentType;
    @JsonProperty(value = "shipment_weight")
    private String shipmentWeight;
    @JsonProperty(value = "shipment_weight_unit")
    private String shipmentWeightUnit;
    @JsonProperty(value = "tracking_postal_code")
    private String trackingPostalCode;
    private String tag;
    private String title;
    @JsonProperty(value = "tracked_count")
    private int trackedCount;
    private List<ASCheckpoint> checkpoints;
}

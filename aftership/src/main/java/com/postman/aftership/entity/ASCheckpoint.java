package com.postman.aftership.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Created by antonsakhno on 19.02.17.
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ASCheckpoint {
    private String slug;
    private String city;
    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;
    @JsonProperty(value = "country_name")
    private String countryName;
    private String message;
    @JsonProperty(value = "country_iso3")
    private String countryIso3;
    private String tag;
    @JsonProperty(value = "checkpoint_time")
    private LocalDateTime checkpointTime;
}

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
public class ASMultiTrackData {
    private int page;
    private int limit;
    private int count;
    private String keyword;
    private String slug;
    private List<String> origin;
    private List<String> destination;
    private String tag;
    private String fields;
    @JsonProperty(value = "created_at_min")
    private LocalDateTime createdAtMin;
    @JsonProperty(value = "created_at_max")
    private LocalDateTime createdAtMax;
    private List<ASTracking> trackings;
}

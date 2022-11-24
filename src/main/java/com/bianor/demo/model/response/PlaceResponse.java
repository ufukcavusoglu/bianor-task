package com.bianor.demo.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlaceResponse {

    @JsonProperty("place name")
    private String placeName;
    private Double longitude;
    private String state;
    @JsonProperty("state abbreviation")
    private String stateAbbreviation;
    private Double latitude;

}

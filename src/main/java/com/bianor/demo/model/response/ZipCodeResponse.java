package com.bianor.demo.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ZipCodeResponse {

    @JsonProperty("post code")
    private String zipCode;
    private String country;
    @JsonProperty("country abbreviation")
    private String countryAbbreviation;
    private List<PlaceResponse> places;

}

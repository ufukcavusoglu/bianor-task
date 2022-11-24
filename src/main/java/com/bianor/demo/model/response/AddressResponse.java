package com.bianor.demo.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    @JsonProperty("zip code")
    private String zipCode;
    @JsonProperty("place name")
    private String placeName;
    private Double longitude;
    private Double latitude;
    private Boolean special;

}

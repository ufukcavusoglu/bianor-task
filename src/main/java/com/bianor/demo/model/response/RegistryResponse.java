package com.bianor.demo.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RegistryResponse {

    @JsonProperty("user name")
    private String userName;
    private List<AddressResponse> addresses;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("registered at")
    private LocalDateTime registeredAt;
}

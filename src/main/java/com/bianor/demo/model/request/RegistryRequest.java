package com.bianor.demo.model.request;

import lombok.Data;

import java.util.List;

@Data
public class RegistryRequest {

    private String username;
    private List<Integer> zipCodes;
}

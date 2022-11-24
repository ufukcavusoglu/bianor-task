package com.bianor.demo.client;

import com.bianor.demo.model.response.ZipCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "zippopotan-client", url = "${zippopotan.url}")
public interface ZippoptanClient {

    @GetMapping(value = "/{zipCode}")
    ResponseEntity<ZipCodeResponse> getZipCodeInfo(@PathVariable("zipCode") String zipCode);

}

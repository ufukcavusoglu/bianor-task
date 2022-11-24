package com.bianor.demo.controller;

import com.bianor.demo.model.entity.Registry;
import com.bianor.demo.model.mapper.RegistryMapper;
import com.bianor.demo.model.request.RegistryRequest;
import com.bianor.demo.model.response.RegistryResponse;
import com.bianor.demo.service.RegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class RegistryController {

    private final RegistryService registryService;
    private final RegistryMapper registryMapper;

    @PostMapping("/registry")
    public ResponseEntity<Void> registry(@RequestBody RegistryRequest registryRequest){
        Registry registry = registryMapper.toRegistry(registryRequest);
        registryService.registry(registry);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/registry/{userName}")
    public ResponseEntity<List<RegistryResponse>> getRegistryWithAddressByUserName(@PathVariable("userName") String userName){
        List<Registry> userWithAllAddressByRegistryName = registryService.getUserWithAllAddressByUserName(userName);
        List<RegistryResponse> registryResponses = registryMapper.toRegistryResponse(userWithAllAddressByRegistryName);
        return ResponseEntity.ok(registryResponses);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<RegistryResponse> getUserWithLatestRegistryAddress(@PathVariable("userName") String userName){
        Registry userWithAllAddressByRegistryName = registryService.getUserWithLatestRegistryAddress(userName);
        RegistryResponse registryResponses = registryMapper.toRegistryResponse(userWithAllAddressByRegistryName);
        return ResponseEntity.ok(registryResponses);
    }

}

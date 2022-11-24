package com.bianor.demo.model.mapper;

import com.bianor.demo.model.entity.Address;
import com.bianor.demo.model.entity.Registry;
import com.bianor.demo.model.request.RegistryRequest;
import com.bianor.demo.model.response.RegistryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class RegistryMapper {

    @Mapping(source = "username" ,target = "user.name")
    @Mapping(source = "registryRequest", target = "addresses", qualifiedByName = "addresses")
    public abstract Registry toRegistry(RegistryRequest registryRequest);

    @Named("addresses")
    List<Address> getAddresses(RegistryRequest registryRequest) {
        return registryRequest.getZipCodes().stream().map(code -> {
            Address address = new Address();
            address.setZipCode(code.toString());
            return address;
        }).collect(Collectors.toList());
    }

    @Mapping(source = "user.name" ,target = "userName")
    public abstract RegistryResponse toRegistryResponse(Registry registry);

    public abstract List<RegistryResponse> toRegistryResponse(List<Registry> registry);

}

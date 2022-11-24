package com.bianor.demo.model.mapper;

import com.bianor.demo.model.entity.Address;
import com.bianor.demo.model.response.PlaceResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface PlaceMapper {

    @InheritInverseConfiguration
    void updateAddress(PlaceResponse placeResponse, @MappingTarget Address address);
}

package com.bianor.demo.service;

import com.bianor.demo.client.ZippoptanClient;
import com.bianor.demo.model.entity.Address;
import com.bianor.demo.model.entity.Registry;
import com.bianor.demo.model.entity.RegistryEntityGraph;
import com.bianor.demo.model.entity.User;
import com.bianor.demo.model.mapper.PlaceMapper;
import com.bianor.demo.model.response.PlaceResponse;
import com.bianor.demo.model.response.ZipCodeResponse;
import com.bianor.demo.repository.RegistryRepository;
import com.bianor.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistryService {

    private final RegistryRepository registryRepository;
    private final ZippoptanClient zippoptanClient;
    private final PlaceMapper placeMapper;

    private final UserRepository userRepository;

    @Transactional
    public void registry(Registry registry){
        for (Address address : registry.getAddresses()) {
            address.setRegistry(registry);
            ZipCodeResponse zipCodeResponse = zippoptanClient.getZipCodeInfo(address.getZipCode()).getBody();
            if(Objects.nonNull(zipCodeResponse)) {
                address.setSpecial(isSpecial(zipCodeResponse.getZipCode()));
                List<PlaceResponse> placeResponses = zipCodeResponse.getPlaces();
                if (!CollectionUtils.isEmpty(placeResponses))
                    placeMapper.updateAddress(placeResponses.get(0), address);
            }
        }
        userRepository.findById(registry.getUser().getName()).ifPresent(registry::setUser);
        registryRepository.save(registry);
    }

    public List<Registry> getUserWithAllAddressByUserName(String userName){
        RegistryEntityGraph registryEntityGraph = RegistryEntityGraph.____().addresses().____.user().____.____();
        return registryRepository.findByUserName(userName, registryEntityGraph);
    }

    public Registry getUserWithLatestRegistryAddress(String userName){
        RegistryEntityGraph registryEntityGraph = RegistryEntityGraph.____().addresses().____.user().____.____();
        return registryRepository.findTopByUserNameOrderByRegisteredAtDesc(userName, registryEntityGraph)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
    }

    public boolean isSpecial(String zipCode){
        int length = zipCode.length();

        char[] chars = zipCode.toCharArray();

        for(int i = 0; i<length-2;i++ ) {
            char firstNumber = chars[i];
            char secondNumber = chars[i + 1];
            char thirdNumber = chars[i + 2];
            if (firstNumber==secondNumber && secondNumber == thirdNumber) {
                return true;
            }
        }
        return false;
    }

}

package com.bianor.demo;

import com.bianor.demo.model.entity.Address;
import com.bianor.demo.model.entity.Registry;
import com.bianor.demo.model.entity.RegistryEntityGraph;
import com.bianor.demo.model.request.RegistryRequest;
import com.bianor.demo.repository.RegistryRepository;
import com.bianor.demo.service.RegistryService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistryTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RegistryRepository repository;

    @Autowired
    private RegistryService service;

    @AfterAll
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateWithFalseSpecialFlag() throws Exception {
        RegistryRequest registryRequest = new RegistryRequest();
        registryRequest.setUsername(UUID.randomUUID().toString());
        registryRequest.setZipCodes(List.of(33162));

        ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mvc.perform(post("/users/registry").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(registryRequest)));
        RegistryEntityGraph registryEntityGraph = RegistryEntityGraph.____().user().____.addresses().____.____();
        List<Registry> found = repository.findByUserName(registryRequest.getUsername(), registryEntityGraph);
        assertThat(found.size()).isEqualTo(1);
        Registry registry = found.get(0);

        Condition<Address> isSpecial = new Condition<>(Address::getSpecial, "is special");

        assertThat(registry).extracting(Registry::getAddresses)
                .extracting(addres -> addres.get(0))
                .isNot(isSpecial);
    }


    @Test
    public void whenValidInput_thenCreateWithTrueSpecialFlag() throws Exception {
        RegistryRequest registryRequest = new RegistryRequest();
        registryRequest.setUsername(UUID.randomUUID().toString());
        registryRequest.setZipCodes(List.of(20002));

        ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mvc.perform(post("/users/registry").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(registryRequest)));
        RegistryEntityGraph registryEntityGraph = RegistryEntityGraph.____().user().____.addresses().____.____();
        List<Registry> found = repository.findByUserName(registryRequest.getUsername(), registryEntityGraph);
        assertThat(found.size()).isEqualTo(1);
        Registry registry = found.get(0);

        Condition<Address> isSpecial = new Condition<>(Address::getSpecial, "is special");

        assertThat(registry).extracting(Registry::getAddresses)
                .extracting(addres -> addres.get(0))
                .is(isSpecial);
    }


    @Test
    public void createRegistryWithTwoAddress() throws Exception {
        RegistryRequest registryRequest = new RegistryRequest();
        registryRequest.setUsername(UUID.randomUUID().toString());
        registryRequest.setZipCodes(List.of(20002,33162));

        ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mvc.perform(post("/users/registry").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(registryRequest)));
        RegistryEntityGraph registryEntityGraph = RegistryEntityGraph.____().user().____.addresses().____.____();
        List<Registry> found = repository.findByUserName(registryRequest.getUsername(), registryEntityGraph);
        assertThat(found.size()).isEqualTo(1);
        Registry registry = found.get(0);

        assertThat(registry).extracting(Registry::getAddresses)
                .is(new Condition<>(address -> address.size() == 2, "rigth amount of address"));
    }

    @Test
    public void createTwoRegistrySequentiallyWithSameUserName_ThenCheckTheRegistryCount() {
        RegistryRequest registryRequest = new RegistryRequest();
        registryRequest.setUsername(UUID.randomUUID().toString());
        registryRequest.setZipCodes(List.of(20002,33162));

        ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        IntStream.rangeClosed(1,2).forEach(counter -> {
            try {
                mvc.perform(post("/users/registry").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(registryRequest)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        RegistryEntityGraph registryEntityGraph = RegistryEntityGraph.____().user().____.addresses().____.____();
        List<Registry> found = repository.findByUserName(registryRequest.getUsername(), registryEntityGraph);


        assertThat(found.size()).isEqualTo(2);
        assertThat(found).flatMap(Registry::getAddresses)
                .is(new Condition<>(addresses -> addresses.size() == 4, "right amount of address"));
    }

    @Test
    public void createTwoRegistrySequentiallyWithSameUserName_ThenCreateJson() throws Exception {
        RegistryRequest registryRequest = new RegistryRequest();
        registryRequest.setUsername(UUID.randomUUID().toString());
        registryRequest.setZipCodes(List.of(20002,33162));

        ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        IntStream.rangeClosed(1,2).forEach(counter -> {
            try {
                mvc.perform(post("/users/registry").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(registryRequest)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Registry latestRegistry1 = service.getUserWithAllAddressByUserName(registryRequest.getUsername())
                .stream().max(Comparator.comparing(Registry::getRegisteredAt))
                .orElseThrow(() -> new RuntimeException("no content"));

        Registry latestRegistry2 = service.getUserWithLatestRegistryAddress(registryRequest.getUsername());
        assertThat(latestRegistry1).isEqualTo(latestRegistry2);
    }

}
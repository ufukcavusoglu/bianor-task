package com.bianor.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "RegistryUser")
public class User {

    @Id
    private String name;
    @OneToMany(mappedBy = Registry.Fields.user, cascade = CascadeType.ALL)
    private List<Registry> registries = new ArrayList<>();

}

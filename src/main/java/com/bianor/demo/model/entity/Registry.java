package com.bianor.demo.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@FieldNameConstants
@Table(name = "Registry")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Registry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @OneToMany(mappedBy = Address.Fields.registry, cascade = CascadeType.ALL)
    private List<Address> addresses;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registeredAt;


}

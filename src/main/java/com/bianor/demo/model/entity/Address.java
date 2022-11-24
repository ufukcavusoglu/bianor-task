package com.bianor.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@FieldNameConstants
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String zipCode;
    private String placeName;
    private Double longitude;
    private Double latitude;
    private Boolean special;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Registry registry;
}

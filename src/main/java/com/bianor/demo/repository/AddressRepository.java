package com.bianor.demo.repository;

import com.bianor.demo.model.entity.Address;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends EntityGraphJpaRepository<Address, Long> {
}

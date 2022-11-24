package com.bianor.demo.repository;

import com.bianor.demo.model.entity.Registry;
import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistryRepository extends JpaRepository<Registry,Long>, EntityGraphJpaRepository<Registry, Long> {

    List<Registry> findByUserName(String userName, EntityGraph entityGraph);

    Optional<Registry> findTopByUserNameOrderByRegisteredAtDesc(String userNaem, EntityGraph entityGraph);

}

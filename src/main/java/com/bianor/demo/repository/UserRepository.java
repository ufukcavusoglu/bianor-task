package com.bianor.demo.repository;

import com.bianor.demo.model.entity.User;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;

public interface UserRepository extends EntityGraphJpaRepository<User,String> {
}

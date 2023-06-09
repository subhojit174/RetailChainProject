package com.retail.retailChain.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.retailChain.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}

package com.bytes7.feature_flag7.repository;

import com.bytes7.feature_flag7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean exitsByUsername(String username);
}

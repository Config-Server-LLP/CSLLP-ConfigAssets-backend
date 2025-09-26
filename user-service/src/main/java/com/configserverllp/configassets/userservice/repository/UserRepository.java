package com.configserverllp.configassets.userservice.repository;

import com.configserverllp.configassets.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username
    Optional<User> findByUsername(String username);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Find all active users
    List<User> findByActiveTrue();

    // Find all users by role
    List<User> findByRole(String role);

    // Check if a user exists with email
    boolean existsByEmail(String email);

    // Check if a user exists with username
    boolean existsByUsername(String username);
}

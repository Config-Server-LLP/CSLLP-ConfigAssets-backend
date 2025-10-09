package com.configserverllp.configassets.userservice.controller;

import com.configserverllp.configassets.userservice.dto.UserDTO;
import com.configserverllp.configassets.userservice.exception.DuplicateUserException;
import com.configserverllp.configassets.userservice.exception.InvalidUserDataException;
import com.configserverllp.configassets.userservice.exception.UserNotFoundException;
import com.configserverllp.configassets.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        logger.info("Received request to create user: {}", userDTO.getUsername());
        try {
            UserDTO savedUser = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (DuplicateUserException e) {
            logger.error("Duplicate user creation attempt: {}", e.getMessage());
            throw e;
        } catch (InvalidUserDataException e) {
            logger.error("Invalid user data: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while creating user", e);
            throw e;
        }
    }

    // ================= HEALTH CHECK =================
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("User service is running!");
    }

    // ================= READ =================
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        logger.info("Fetching user with username: {}", username);
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Fetching all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserDTO>> getActiveUsers() {
        logger.info("Fetching all active users");
        return ResponseEntity.ok(userService.getActiveUsers());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable String role) {
        logger.info("Fetching users by role: {}", role);
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        logger.info("Updating user with ID: {}", id);
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            logger.error("User not found while updating: {}", e.getMessage());
            throw e;
        } catch (InvalidUserDataException e) {
            logger.error("Invalid data while updating user: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while updating user", e);
            throw e;
        }
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            logger.error("User not found while deleting: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while deleting user", e);
            throw e;
        }
    }
}

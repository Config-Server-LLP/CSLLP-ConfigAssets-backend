package com.configserverllp.configassets.userservice.service.impl;

import com.configserverllp.configassets.userservice.dto.UserDTO;
import com.configserverllp.configassets.userservice.entity.User;
import com.configserverllp.configassets.userservice.exception.DuplicateUserException;
import com.configserverllp.configassets.userservice.exception.InvalidUserDataException;
import com.configserverllp.configassets.userservice.exception.UserNotFoundException;
import com.configserverllp.configassets.userservice.repository.UserRepository;
import com.configserverllp.configassets.userservice.service.UserService;
import com.configserverllp.configassets.userservice.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        try {
            logger.info("Creating user with username: {}", userDTO.getUsername());
            
            // Validate required fields
            validateUserData(userDTO);
            
            // Check for duplicates
            if (userRepository.existsByUsername(userDTO.getUsername())) {
                throw new DuplicateUserException("Username already exists: " + userDTO.getUsername());
            }
            
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new DuplicateUserException("Email already exists: " + userDTO.getEmail());
            }
            
            User user = UserMapper.toEntity(userDTO);
            
            // Set auditing fields manually if needed
            if (user.getCreatedAt() == null) {
                user.setCreatedAt(java.time.LocalDateTime.now());
            }
            if (user.getCreatedBy() == null) {
                user.setCreatedBy("system");
            }
            
            User savedUser = userRepository.save(user);
            return UserMapper.toDTO(savedUser);
        } catch (DuplicateUserException | InvalidUserDataException e) {
            throw e; // Re-throw custom exceptions
        } catch (Exception e) {
            logger.error("Error while creating user: {}", e.getMessage(), e);
            throw new InvalidUserDataException("Failed to create user: " + e.getMessage());
        }
    }
    
    private void validateUserData(UserDTO userDTO) {
        if (userDTO == null) {
            throw new InvalidUserDataException("User data cannot be null");
        }
        
        if (userDTO.getUsername() == null || userDTO.getUsername().trim().isEmpty()) {
            throw new InvalidUserDataException("Username is required");
        }
        
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new InvalidUserDataException("Email is required");
        }
        
        // Basic email format validation
        if (!isValidEmail(userDTO.getEmail())) {
            throw new InvalidUserDataException("Invalid email format");
        }
        
        if (userDTO.getFirstName() == null || userDTO.getFirstName().trim().isEmpty()) {
            throw new InvalidUserDataException("First name is required");
        }
        
        if (userDTO.getRole() == null || userDTO.getRole().trim().isEmpty()) {
            throw new InvalidUserDataException("Role is required");
        }
        
        // Set default active status if not provided
        if (userDTO.getActive() == null) {
            userDTO.setActive(true);
        }
    }
    
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setFirstName(userDTO.getFirstName());
            existingUser.setLastName(userDTO.getLastName());
            existingUser.setPhone(userDTO.getPhone());
            existingUser.setRole(userDTO.getRole());
            existingUser.setActive(userDTO.getActive());

            logger.info("Updating user with ID: {}", id);
            User updatedUser = userRepository.save(existingUser);
            return UserMapper.toDTO(updatedUser);
        }).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        logger.info("Deleting user with ID: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDTO);
    }

    @Override
    public Optional<UserDTO> getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(UserMapper::toDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .sorted((u1, u2) -> u1.getUsername().compareToIgnoreCase(u2.getUsername()))
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getActiveUsers() {
        return userRepository.findByActiveTrue()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUsersByRole(String role) {
        return userRepository.findByRole(role)
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}

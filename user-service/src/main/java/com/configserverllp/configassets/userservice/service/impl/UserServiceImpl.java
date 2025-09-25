package com.configserverllp.configassets.userservice.service.impl;

import com.configserverllp.configassets.userservice.dto.UserDTO;
import com.configserverllp.configassets.userservice.entity.User;
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
            User user = UserMapper.toEntity(userDTO);
            User savedUser = userRepository.save(user);
            return UserMapper.toDTO(savedUser);
        } catch (Exception e) {
            logger.error("Error while creating user: {}", e.getMessage(), e);
            throw e; // handled by global handler
        }
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

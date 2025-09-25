package com.configserverllp.configassets.userservice.service;

import com.configserverllp.configassets.userservice.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    Optional<UserDTO> getUserById(Long id);
    Optional<UserDTO> getUserByUsername(String username);
    List<UserDTO> getAllUsers();
    List<UserDTO> getActiveUsers();
    List<UserDTO> getUsersByRole(String role);
}

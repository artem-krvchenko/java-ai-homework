package com.innowise.service.impl;

import com.innowise.dto.UserDto;
import com.innowise.exception.ResourceNotFoundException;
import com.innowise.exception.DuplicateResourceException;
import com.innowise.mapper.UserMapper;
import com.innowise.model.User;
import com.innowise.repository.UserRepository;
import com.innowise.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        log.debug("Retrieving all users from repository");
        List<User> users = userRepository.findAll();
        log.debug("Found {} users", users.size());
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        log.debug("Retrieving user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });
        log.debug("Found user: {}", user.getUsername());
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        log.debug("Retrieving user with username: {}", username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found with username: {}", username);
            throw new ResourceNotFoundException("User not found with username: " + username);
        }
        log.debug("Found user: {}", user.getUsername());
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        log.debug("Retrieving user with email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("User not found with email: {}", email);
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        log.debug("Found user: {}", user.getUsername());
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        log.debug("Creating new user: {}", userDto.getUsername());
        
        // Check if username exists
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            log.error("Username already exists: {}", userDto.getUsername());
            throw new DuplicateResourceException("Username already exists: " + userDto.getUsername());
        }

        // Check if email exists
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            log.error("Email already exists: {}", userDto.getEmail());
            throw new DuplicateResourceException("Email already exists: " + userDto.getEmail());
        }

        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        log.info("Created new user: {}", savedUser.getUsername());
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        log.debug("Updating user with id: {}", id);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });

        // Check if new username is taken by another user
        User userWithUsername = userRepository.findByUsername(userDto.getUsername());
        if (userWithUsername != null && !userWithUsername.getId().equals(id)) {
            log.error("Username already exists: {}", userDto.getUsername());
            throw new DuplicateResourceException("Username already exists: " + userDto.getUsername());
        }

        // Check if new email is taken by another user
        User userWithEmail = userRepository.findByEmail(userDto.getEmail());
        if (userWithEmail != null && !userWithEmail.getId().equals(id)) {
            log.error("Email already exists: {}", userDto.getEmail());
            throw new DuplicateResourceException("Email already exists: " + userDto.getEmail());
        }

        User updatedUser = userMapper.toEntity(userDto);
        updatedUser.setId(id);
        User savedUser = userRepository.save(updatedUser);
        log.info("Updated user: {}", savedUser.getUsername());
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.debug("Deleting user with id: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });

        userRepository.deleteById(id);
        log.info("Deleted user: {}", user.getUsername());
    }
} 
package com.innowise.service;

import com.innowise.dto.UserDto;
import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto getUserByUsername(String username);
    UserDto getUserByEmail(String email);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
} 
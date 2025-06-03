package com.innowise.controller;

import com.innowise.dto.UserDto;
import com.innowise.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.debug("Fetching all users");
        List<UserDto> users = userService.getAllUsers();
        log.info("Retrieved {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "ID of the user to retrieve", required = true)
            @PathVariable Long id) {
        log.debug("Fetching user with id: {}", id);
        UserDto user = userService.getUserById(id);
        log.info("Retrieved user: {}", user.getUsername());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username", description = "Retrieves a user by their username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> getUserByUsername(
            @Parameter(description = "Username of the user to retrieve", required = true)
            @PathVariable String username) {
        log.debug("Fetching user with username: {}", username);
        UserDto user = userService.getUserByUsername(username);
        log.info("Retrieved user: {}", user.getUsername());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieves a user by their email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> getUserByEmail(
            @Parameter(description = "Email of the user to retrieve", required = true)
            @PathVariable String email) {
        log.debug("Fetching user with email: {}", email);
        UserDto user = userService.getUserByEmail(email);
        log.info("Retrieved user: {}", user.getUsername());
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    public ResponseEntity<UserDto> createUser(
            @Parameter(description = "User details to create", required = true)
            @Valid @RequestBody UserDto userDto) {
        log.debug("Creating new user: {}", userDto.getUsername());
        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user", description = "Updates an existing user's details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "ID of the user to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated user details", required = true)
            @Valid @RequestBody UserDto userDto) {
        log.debug("Updating user with id: {}", id);
        UserDto updatedUser = userService.updateUser(id, userDto);
        log.info("Updated user: {}", updatedUser.getUsername());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User successfully deleted"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true)
            @PathVariable Long id) {
        log.debug("Deleting user with id: {}", id);
        userService.deleteUser(id);
        log.info("Deleted user with id: {}", id);
        return ResponseEntity.noContent().build();
    }
} 
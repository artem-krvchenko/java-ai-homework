package com.innowise.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login request data")
public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(description = "User's password", example = "Password123!")
    private String password;
} 
package com.innowise.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Authentication response data")
public class AuthResponse {
    @Schema(description = "JWT access token")
    private String accessToken;

    @Schema(description = "User's name")
    private String name;

    @Schema(description = "User's email")
    private String email;
} 
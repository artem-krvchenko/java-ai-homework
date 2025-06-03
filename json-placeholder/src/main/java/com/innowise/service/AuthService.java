package com.innowise.service;

import com.innowise.dto.auth.AuthResponse;
import com.innowise.dto.auth.LoginRequest;
import com.innowise.dto.auth.RegisterRequest;

public interface AuthService {
    /**
     * Register a new user
     * @param request Registration request containing user details
     * @return Authentication response with JWT token
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Authenticate a user and generate JWT token
     * @param request Login request containing credentials
     * @return Authentication response with JWT token
     */
    AuthResponse login(LoginRequest request);
} 
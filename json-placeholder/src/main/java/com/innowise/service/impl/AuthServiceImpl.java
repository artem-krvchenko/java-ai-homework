package com.innowise.service.impl;

import com.innowise.dto.auth.AuthResponse;
import com.innowise.dto.auth.LoginRequest;
import com.innowise.dto.auth.RegisterRequest;
import com.innowise.exception.DuplicateResourceException;
import com.innowise.exception.ResourceNotFoundException;
import com.innowise.model.AuthUser;
import com.innowise.repository.AuthUserRepository;
import com.innowise.security.JwtService;
import com.innowise.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (authUserRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + request.getEmail());
        }

        AuthUser authUser = new AuthUser();
        authUser.setName(request.getName());
        authUser.setEmail(request.getEmail());
        authUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        AuthUser savedUser = authUserRepository.save(authUser);
        String token = jwtService.generateToken(createUserDetails(savedUser));

        return new AuthResponse(token, savedUser.getName(), savedUser.getEmail());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        try {
            AuthUser authUser = authUserRepository.findByEmail(request.getEmail());
            String token = jwtService.generateToken(createUserDetails(authUser));
            return new AuthResponse(token, authUser.getName(), authUser.getEmail());
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not found with email: " + request.getEmail());
        }
    }

    private UserDetails createUserDetails(AuthUser authUser) {
        return new User(
                authUser.getEmail(),
                authUser.getPasswordHash(),
                new ArrayList<>()
        );
    }
} 
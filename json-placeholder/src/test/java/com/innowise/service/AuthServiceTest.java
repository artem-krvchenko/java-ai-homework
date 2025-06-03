package com.innowise.service;

import com.innowise.dto.auth.AuthResponse;
import com.innowise.dto.auth.LoginRequest;
import com.innowise.dto.auth.RegisterRequest;
import com.innowise.exception.ResourceNotFoundException;
import com.innowise.exception.DuplicateResourceException;
import com.innowise.model.AuthUser;
import com.innowise.repository.AuthUserRepository;
import com.innowise.security.JwtService;
import com.innowise.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthUserRepository authUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private AuthUser authUser;
    private String token;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setName("Test User");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        authUser = new AuthUser();
        authUser.setId(1L);
        authUser.setName("Test User");
        authUser.setEmail("test@example.com");
        authUser.setPasswordHash("encodedPassword");

        token = "test.jwt.token";
    }

    @Test
    void register_WhenUserDoesNotExist_ShouldCreateUser() {
        when(authUserRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(authUserRepository.save(any(AuthUser.class))).thenReturn(authUser);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(token);

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals(token, response.getAccessToken());
        assertEquals(authUser.getName(), response.getName());
        assertEquals(authUser.getEmail(), response.getEmail());
        verify(authUserRepository, times(1)).save(any(AuthUser.class));
    }

    @Test
    void register_WhenEmailExists_ShouldThrowException() {
        when(authUserRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> authService.register(registerRequest));
        verify(authUserRepository, never()).save(any(AuthUser.class));
    }

    @Test
    void login_WhenCredentialsAreValid_ShouldReturnToken() {
        when(authUserRepository.findByEmail(loginRequest.getEmail())).thenReturn(authUser);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(authUser, null));
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(token);

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals(token, response.getAccessToken());
        assertEquals(authUser.getName(), response.getName());
        assertEquals(authUser.getEmail(), response.getEmail());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void login_WhenUserDoesNotExist_ShouldThrowException() {
        when(authUserRepository.findByEmail(loginRequest.getEmail())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> authService.login(loginRequest));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
} 
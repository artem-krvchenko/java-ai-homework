package com.innowise.security;

import com.innowise.model.AuthUser;
import com.innowise.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            AuthUser authUser = authUserRepository.findByEmail(email);
            return new User(
                    authUser.getEmail(),
                    authUser.getPasswordHash(),
                    new ArrayList<>()
            );
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
} 
package com.pedro.usersecurityservice.services;

import com.pedro.usersecurityservice.domain.UserEntity;
import com.pedro.usersecurityservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getPrincipal() instanceof String && authentication.getCredentials() instanceof String) {
            UserEntity user = userRepository.findByUsername((String) authentication.getPrincipal()).orElseThrow();
            if (passwordEncoder.matches((String) authentication.getCredentials(), user.getPassword())) {
                return authentication;
            }
        }
        throw new RuntimeException("Could not verify user");
    }
}

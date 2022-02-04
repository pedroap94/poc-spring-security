package com.pedro.usersecurityservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pedro.usersecurityservice.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JWTAuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private static final String secret = "secret";

    public String getJWT(UserDto userDto) {
        try {
            UserDetails user = userDetailsService.loadUserByUsername(userDto.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDto.getUsername(),
                    userDto.getPassword(),
                    user.getAuthorities()
            ));
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("User not found");
        }
        long tenHours = 36000000L;
        return JWT.create().withSubject(userDto.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + tenHours))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public UsernamePasswordAuthenticationToken verify(String token){
        String username = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build()
                .verify(token)
                .getSubject();
        assert username != null : null;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}

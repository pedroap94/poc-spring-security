package com.pedro.usersecurityservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pedro.usersecurityservice.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JWTAuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final String SECRET = "secret";

    public String getJWT(UserDto userDto) {
        try {
            UserDetails user = userDetailsService.loadUserByUsername(userDto.getUsername());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDto.getUsername(),
                    userDto.getPassword(),
                    user.getAuthorities()
            ));
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("User not found");
        }
        long TEN_HOURS = 36000000L;
        return JWT.create().withSubject(userDto.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TEN_HOURS))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    public UsernamePasswordAuthenticationToken verify(String token){
        String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
        assert username instanceof String : null;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}

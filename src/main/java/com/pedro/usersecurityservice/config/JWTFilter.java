package com.pedro.usersecurityservice.config;

import com.pedro.usersecurityservice.services.JWTAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends BasicAuthenticationFilter {
    JWTAuthService jwtAuthService;

    public JWTFilter(AuthenticationManager authenticationManager, JWTAuthService jwtAuthService) {
        super(authenticationManager);
        this.jwtAuthService = jwtAuthService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationheader = request.getHeader("Authorization");
        String token = authorizationheader.substring(7);
        UsernamePasswordAuthenticationToken auth = jwtAuthService.verify(token);

        SecurityContextHolder.getContext()
                .setAuthentication(auth);
        chain.doFilter(request, response);
    }
}

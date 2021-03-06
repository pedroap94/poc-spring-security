package com.pedro.usersecurityservice.config;

import com.pedro.usersecurityservice.services.JWTAuthService;
import com.pedro.usersecurityservice.services.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userSecurity;
    private AuthenticationManager authenticationManager;
    private JWTAuthService authService;
    private PasswordEncoder passwordEncoder;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/user/admin-secret").hasAnyRole("ADMIN")
                .anyRequest().fullyAuthenticated()
                .and().addFilter(new JWTFilter(authenticationManager, authService));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/api/v1/user/signup")
                .antMatchers("/api/v1/user/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurity).passwordEncoder(passwordEncoder);
    }
}

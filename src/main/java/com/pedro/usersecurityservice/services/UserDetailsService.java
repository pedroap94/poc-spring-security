package com.pedro.usersecurityservice.services;

import com.pedro.usersecurityservice.domain.UserEntity;
import com.pedro.usersecurityservice.domain.UserSecurityEntity;
import com.pedro.usersecurityservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            return new UserSecurityEntity(user.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}

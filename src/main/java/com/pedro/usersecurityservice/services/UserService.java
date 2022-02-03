package com.pedro.usersecurityservice.services;

import com.pedro.usersecurityservice.domain.UserEntity;
import com.pedro.usersecurityservice.dto.UserDto;
import com.pedro.usersecurityservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public void saveUser(UserDto userDto){
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(modelMapper.map(userDto, UserEntity.class));
    }

    @PreAuthorize("#username == authentication.principal.username")
    public String getSecret(String username){
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        return user.getSecret();
    }

}

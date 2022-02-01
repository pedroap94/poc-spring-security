package com.pedro.usersecurityservice.services;

import com.pedro.usersecurityservice.domain.UserEntity;
import com.pedro.usersecurityservice.dto.UserDto;
import com.pedro.usersecurityservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    public void saveUser(UserDto userDto){
        userRepository.save(modelMapper.map(userDto, UserEntity.class));
    }

    public String getSecret(String username){
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        return user.getSecret();
    }

}

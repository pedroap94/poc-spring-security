package com.pedro.usersecurityservice.controllers;

import com.pedro.usersecurityservice.dto.UserDto;
import com.pedro.usersecurityservice.services.UserDetailsService;
import com.pedro.usersecurityservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<Void> signUp(@RequestBody UserDto userDto){
        userService.saveUser(userDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping("hello")
    public ResponseEntity<String> helloWorld(){
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @GetMapping("admin-secret")
    public ResponseEntity<Integer> getAdminSecret(@RequestParam String username){
        return new ResponseEntity<>(userService.getSecret(), HttpStatus.ACCEPTED);
    }
}

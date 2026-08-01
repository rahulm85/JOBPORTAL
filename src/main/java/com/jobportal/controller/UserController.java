package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobportal.dto.LoginRequestDTO;
import com.jobportal.dto.LoginResponseDTO;
import com.jobportal.dto.RegisterRequestDTO;
import com.jobportal.dto.UserResponseDTO;
import com.jobportal.service.impl.UserService;


@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request) {

        return userService.register(request);

    }



    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {

        return userService.login(request);

    }



    @GetMapping("/users/{id}")
    public UserResponseDTO getUser(
            @PathVariable Long id
    ) {

        return userService.getUserById(id);

    }

}
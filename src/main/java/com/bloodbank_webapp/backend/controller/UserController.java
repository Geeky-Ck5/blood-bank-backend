package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.dto.UserDTO;
import com.bloodbank_webapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return "User registered successfully.";
    }

    @PostMapping("/activate/{token}")
    public String activateUser(@PathVariable String token) {
        userService.activateUser(token);
        return "User activated successfully.";
    }
}

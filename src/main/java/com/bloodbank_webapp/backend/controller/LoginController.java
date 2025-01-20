package com.bloodbank_webapp.backend.controller;
import com.bloodbank_webapp.backend.model.Users;
import com.bloodbank_webapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bloodbank_webapp.backend.dto.LoginDTO;

import java.util.Map;


@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginRequest) {
        Users user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password."));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Login successful.",
                "userId", String.valueOf(user.getUserId()),
                "email", user.getEmail(),
                "role", user.getRole()
        ));
    }
}
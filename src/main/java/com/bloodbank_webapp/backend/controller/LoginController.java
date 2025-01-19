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
        try {
            Users user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful.",
                    "role", user.getRole() // Convert enum to string
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
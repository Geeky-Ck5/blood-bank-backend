package com.bloodbank_webapp.backend.controller;


import com.bloodbank_webapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.generateAndSendActivationToken(email);
        return ResponseEntity.ok("Activation token sent to your email.");
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String token = request.get("token");

        if (email == null || token == null) {
            throw new IllegalArgumentException("Both email and token are required");
        }

        Map<String, Object> response = userService.validateActivationToken(email, token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/regenerate-token")
    public ResponseEntity<String> regenerateToken(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.generateAndSendActivationToken(email);
        return ResponseEntity.ok("New activation token sent to your email.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        try {
            String response = userService.login(email, password);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/reset-failed-login")
    public ResponseEntity<String> resetFailedLogin(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.resetFailedLoginAttempts(email);
        return ResponseEntity.ok("Failed login attempts reset.");
    }
}
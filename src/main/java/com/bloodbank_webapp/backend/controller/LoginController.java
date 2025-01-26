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

    @PostMapping("/reset-failed-login")
    public ResponseEntity<String> resetFailedLogin(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.resetFailedLoginAttempts(email);
        return ResponseEntity.ok("Failed login attempts have been reset.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        if (email == null || token == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Email, token, and new password are required.");
        }

        try {
            userService.resetPasswordWithToken(email, token, newPassword);
            return ResponseEntity.ok("Password has been reset successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is required."));
        }

        try {
            userService.generateForgotPasswordToken(email);
            return ResponseEntity.ok(Map.of(
                    "message", "A password reset token has been sent to your email.",
                    "status", "success"
            ));
        } catch (UserService.UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", ex.getMessage(),
                    "status", "fail"
            ));
        }
    }


}
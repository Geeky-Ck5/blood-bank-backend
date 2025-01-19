package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.dto.UserDTO;
import com.bloodbank_webapp.backend.dto.SignupRequestDTO;
import com.bloodbank_webapp.backend.dto.ProfileUpdateRequestDTO;
import com.bloodbank_webapp.backend.model.Users;
import com.bloodbank_webapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO signupRequest) {
        userService.signup(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

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

    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestBody ProfileUpdateRequestDTO profileRequest, @AuthenticationPrincipal Users user) {
        userService.updateProfile(user, profileRequest);
        return ResponseEntity.ok("Profile updated successfully.");
    }


}

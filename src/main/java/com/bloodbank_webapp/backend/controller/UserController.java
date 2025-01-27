package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.dto.UserDTO;
import com.bloodbank_webapp.backend.dto.SignupRequestDTO;
import com.bloodbank_webapp.backend.dto.ProfileUpdateRequestDTO;
import com.bloodbank_webapp.backend.model.Center;
import com.bloodbank_webapp.backend.model.Users;
import com.bloodbank_webapp.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequestDTO signupRequest) {
        // Validate role field
        if (!"donor".equalsIgnoreCase(signupRequest.getRole()) &&
                !"recipient".equalsIgnoreCase(signupRequest.getRole())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid role specified. Please choose 'donor' or 'recipient'."));
        }

        userService.signup(signupRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully."));
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
    public ResponseEntity<Map<String, String>> updateProfile(@RequestBody ProfileUpdateRequestDTO profileRequest) {
        try {
            userService.updateProfile(profileRequest);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Profile updated successfully."
            ));
        } catch (UserService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "An error occurred while updating the profile."
            ));
        }
    }



    @PutMapping("/{id}/profile")
    public ResponseEntity<String> updateProfile(@PathVariable Long id, @RequestBody ProfileUpdateRequestDTO profileRequest) {
        Users user = userService.getUserById(id); // Assuming a getUserById method exists
        userService.updateProfile(profileRequest);
        return ResponseEntity.ok("Profile updated successfully.");
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> updateUserStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusRequest) {
        String newStatus = statusRequest.get("status");

        try {
            userService.updateUserStatus(id, newStatus);
            return ResponseEntity.ok(Map.of(
                    "message", "User status updated successfully.",
                    "status", newStatus
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/basic-details")
    public ResponseEntity<List<UserDTO>> getAllUsersWithBasicDetails() {
        List<UserDTO> users = userService.getAllUsersWithBasicDetails();
        return ResponseEntity.ok(users);
    }

}

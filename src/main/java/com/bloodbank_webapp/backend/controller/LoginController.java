package com.bloodbank_webapp.backend.controller;
import com.bloodbank_webapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bloodbank_webapp.backend.dto.LoginDTO;


@RestController
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String response = userService.login(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(response);
    }

}

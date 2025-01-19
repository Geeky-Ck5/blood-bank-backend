package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.ProfileUpdateRequestDTO;
import com.bloodbank_webapp.backend.dto.SignupRequestDTO;
import com.bloodbank_webapp.backend.dto.UserDTO;
import com.bloodbank_webapp.backend.model.Users;
import com.bloodbank_webapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO findUserByEmail(String email) {
        Optional<Users> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return mapToDTO(user.get());
        }
        throw new RuntimeException("User not found");
    }

    public UserDTO activateUser(String token) {
        Optional<Users> user = userRepository.findByActivationToken(token);
        if (user.isPresent()) {
            Users updatedUser = user.get();
            updatedUser.setStatus(Users.Status.ACTIVE);
            updatedUser.setActivationToken(null);
            userRepository.save(updatedUser);
            return mapToDTO(updatedUser);
        }
        throw new RuntimeException("Invalid activation token");
    }

    public void registerUser(UserDTO userDTO) {
        Users user = mapToEntity(userDTO);
        userRepository.save(user);
    }

    // Mapper: Convert Entity to DTO
    private UserDTO mapToDTO(Users user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        dto.setGender(user.getGender().name());
        dto.setNationalId(user.getNationalId());
        dto.setBloodGroup(user.getBloodGroup());
        dto.setEligibilityStatus(user.isEligibilityStatus());
        dto.setStatus(user.getStatus().name());
        dto.setAutoReminders(user.isAutoReminders());
        if (user.getPreferredCenter() != null) {
            dto.setPreferredCenterId(user.getPreferredCenter().getCenterId());
        }
        return dto;
    }

    // Mapper: Convert DTO to Entity
    private Users mapToEntity(UserDTO dto) {
        Users user = new Users();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRole(Users.Role.valueOf(dto.getRole().toUpperCase()));
        user.setGender(Users.Gender.valueOf(dto.getGender().toUpperCase()));
        user.setNationalId(dto.getNationalId());
        user.setBloodGroup(dto.getBloodGroup());
        user.setEligibilityStatus(dto.isEligibilityStatus());
        user.setStatus(Users.Status.valueOf(dto.getStatus().toUpperCase()));
        user.setAutoReminders(dto.isAutoReminders());
        return user;
    }


    public String login(String email, String password) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getStatus().equals(Users.Status.ACTIVE)) {
            throw new RuntimeException("Account is not active.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials.");
        }

        return "Login successful";
    }

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignupRequestDTO signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }

        Users user = new Users();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setStatus(Users.Status.INACTIVE); // Default status
        user.setRole(Users.Role.DONOR); // Default role
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    public void updateProfile(Users user, ProfileUpdateRequestDTO profileRequest) {
        user.setFirstName(profileRequest.getFirstName());
        user.setLastName(profileRequest.getLastName());
        user.setGender(profileRequest.getGender());
        user.setNationalId(profileRequest.getNationalId());
        user.setBloodGroup(profileRequest.getBloodGroup());
        user.setEligibilityStatus(true); // Example logic
        user.setStatus(Users.Status.ACTIVE);

        userRepository.save(user);
    }
}

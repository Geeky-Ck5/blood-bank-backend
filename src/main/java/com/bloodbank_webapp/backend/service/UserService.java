package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.ProfileUpdateRequestDTO;
import com.bloodbank_webapp.backend.dto.SignupRequestDTO;
import com.bloodbank_webapp.backend.dto.UserDTO;
import com.bloodbank_webapp.backend.model.Center;
import com.bloodbank_webapp.backend.model.Users;
import com.bloodbank_webapp.backend.repository.UserRepository;
import com.bloodbank_webapp.backend.repository.CenterRepository;
import jakarta.transaction.Transactional;
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

    @Autowired
    private CenterRepository centerRepository;

    public UserDTO findUserByEmail(String email) {

        Optional<Users> user = userRepository.findByEmail(email.trim().toLowerCase());
        return user.map(this::mapToDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
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
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
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
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
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
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Return a success response or generate a token if using JWT
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

        Users newUser = new Users();
        newUser.setEmail(signupRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        newUser.setStatus(Users.Status.INACTIVE); // Default status
        newUser.setRole(signupRequest.getRole()); // Default role
        newUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(newUser);
    }

    @Transactional
    public void updateProfile(Users user, ProfileUpdateRequestDTO profileRequest) {
        // Validate the user object
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + profileRequest.getUserId());
        }

        // Update user details
        user.setFirstName(profileRequest.getFirstName());
        user.setLastName(profileRequest.getLastName());
        if (profileRequest.getGender() != null) {
            try {
                user.setGender(Users.Gender.valueOf(profileRequest.getGender().trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid gender value: " + profileRequest.getGender());
            }
        }
        user.setNationalId(profileRequest.getNationalId());
        user.setBloodGroup(profileRequest.getBloodGroup());
        user.setEligibilityStatus(profileRequest.isEligibilityStatus());
        user.setStatus(Users.Status.ACTIVE);

        // Save the updated user to the database
        userRepository.save(user);
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }


    public Users authenticate(String email, String password) {
        // Find the user by email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Validate the password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Return the authenticated user
        return user;
    }

    public Users getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }


}

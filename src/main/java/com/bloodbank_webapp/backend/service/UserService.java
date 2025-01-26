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
import java.util.*;

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

        // Check if account is locked
        if (user.getStatus() == Users.Status.INACTIVE) {
            throw new RuntimeException("Account is locked. Please contact support or reset your password.");
        }

        // Check if password is correct
        if (!passwordEncoder.matches(password, user.getPassword())) {
            incrementFailedLogin(email); // Increment failed attempts on incorrect password
            throw new RuntimeException("Invalid email or password");
        }

        // Reset failed attempts after successful login
        resetFailedLoginAttempts(email);

        // Return a success response or JWT token
        return "Login successful!";
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
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Check if account is locked
        if (user.getStatus() == Users.Status.INACTIVE) {
            throw new RuntimeException("Account is locked. Please reset your password.");
        }

        // Check if the password matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            incrementFailedLogin(String.valueOf(user));
            throw new RuntimeException("Invalid email or password");
        }

        // Reset failed login attempts on successful login
        resetFailedLoginAttempts(String.valueOf(user));

        return user;
    }

    public Users getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public void updateUserStatus(Long userId, String newStatus) {
        // Fetch the user by ID
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate the status
        if (!"ACTIVE".equalsIgnoreCase(newStatus) && !"INACTIVE".equalsIgnoreCase(newStatus)) {
            throw new IllegalArgumentException("Invalid status value. Only ACTIVE or INACTIVE are allowed.");
        }

        // Update and save the status
        user.setStatus(Users.Status.valueOf(newStatus.toUpperCase()));
        userRepository.save(user);
    }



    public List<UserDTO> getAllUsersWithBasicDetails() {
        List<Users> users = userRepository.findAllUsers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (Users user : users) {
            userDTOs.add(mapToDTO(user));
        }
        return userDTOs;
    }

    @Autowired
    private EmailService emailService; // Assume EmailService is implemented for sending emails

    public void generateAndSendActivationToken(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Generate 4-digit random token
        String token = String.valueOf((int) (Math.random() * 9000) + 1000);

        // Set token expiry to 5 minutes from now
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        // Update the user's token and expiry in the database
        userRepository.updateActivationToken(email, token, expiry);

        // Construct the validation link
        String validationLink = "http://localhost:4200/validate-token?email=" + email + "&token=" + token;


        // Email subject and body
        String subject = "Your Activation Token";
        String body = String.format(
                "Dear %s,\n\n" +
                        "Your activation token is: %s\n\n" +
                        "Click the link below to validate your token:\n" +
                        "%s\n\n" +
                        "Please note that the token will expire in 5 minutes.\n\n" +
                        "Best regards,\nBloodBank Team",
                user.getFirstName() != null ? user.getFirstName() : "User", // Use first name if available
                token,
                validationLink
        );

        // Send the email
        emailService.sendEmail(email, subject, body);
    }

    public Map<String, Object> validateActivationToken(String email, String token) {
        // Find the user with the matching email and valid token
        Users user = userRepository.findValidTokenByEmail(email, token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        // Activate the user
        userRepository.activateUser(user.getUserId());

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("email", user.getEmail());
        response.put("userId", user.getUserId());

        return response;
    }

    public void incrementFailedLogin(String email) {
        // Find user by email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Increment the failed login attempts
        int failedAttempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(failedAttempts);

        // Lock account if failed attempts exceed 3
        if (failedAttempts >= 3) {
            user.setStatus(Users.Status.INACTIVE); // Set status to INACTIVE
        }

        // Save the updated user
        userRepository.save(user);
    }

    public void resetFailedLoginAttempts(String email) {
        // Find user by email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Reset failed login attempts
        user.setFailedLoginAttempts(0);
        userRepository.save(user);
    }

    public void resetPasswordWithToken(String email, String token, String newPassword) {
        // Find the user by email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Validate the token and expiry
        if (user.getActivationToken() == null || !user.getActivationToken().equals(token)) {
            throw new RuntimeException("Invalid token.");
        }

        if (user.getTokenExpiry() == null || user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired.");
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(newPassword));

        // Clear the token and expiry
        user.setActivationToken(null);
        user.setTokenExpiry(null);

        userRepository.save(user);
    }


    public void forgotPassword(String email) {
        // Find the user by email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with the provided email"));



        userRepository.save(user);

        // Generate the reset link
        String resetLink = "http://localhost:4200//auth/forgot-password";

        // Email subject and body
        String subject = "Password Reset Request";
        String body = String.format(
                "Dear %s,\n\n" +
                        "You recently requested to reset your password. Please click the link below to reset your password:\n\n" +
                        "%s\n\n" +
                        "This link will expire in 15 minutes.\n\n" +
                        "If you did not request a password reset, please ignore this email.\n\n" +
                        "Best regards,\nBloodBank Team",
                user.getFirstName() != null ? user.getFirstName() : "User",
                resetLink
        );

        // Send the email
        emailService.sendEmail(email, subject, body);
    }


    public void generateForgotPasswordToken(String email) {
        // Find the user by email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        // Generate a 4-digit token
        String token = String.format("%04d", new Random().nextInt(10000));

        // Set the token and expiry date
        user.setActivationToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusDays(1)); // 24-hour expiry
        userRepository.save(user);

        // Send the token via email
        String subject = "Password Reset Token";
        String body = String.format(
                "Dear %s,\n\n" +
                        "You recently requested to reset your password. Use the following token to reset your password:\n\n" +
                        "Token: %s\n\n" +
                        "This token will expire in 24 hours.\n\n" +
                        "If you did not request a password reset, please ignore this email.\n\n" +
                        "Best regards,\nBloodBank Team",
                user.getFirstName() != null ? user.getFirstName() : "User",
                token
        );

        emailService.sendEmail(email, subject, body);
    }

}


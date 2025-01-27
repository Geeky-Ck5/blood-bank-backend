package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.ProfileUpdateRequestDTO;
import com.bloodbank_webapp.backend.dto.SignupRequestDTO;
import com.bloodbank_webapp.backend.model.Center;
import com.bloodbank_webapp.backend.dto.UserDTO;
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


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public UserDTO findUserByEmail(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        Optional<Users> user = userRepository.findByEmail(normalizedEmail);
        return user.map(this::mapToDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + normalizedEmail));
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

    public String login(String email, String password) {
        String normalizedEmail = email.trim().toLowerCase();
        Users user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (user.getStatus() == Users.Status.INACTIVE) {
            throw new RuntimeException("Account is locked. Please contact support or reset your password.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            incrementFailedLogin(normalizedEmail);
            throw new RuntimeException("Invalid email or password");
        }

        resetFailedLoginAttempts(normalizedEmail);
        return "Login successful!";
    }

    public void incrementFailedLogin(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        Users user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + normalizedEmail));

        int failedAttempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(failedAttempts);

        if (failedAttempts >= 3) {
            user.setStatus(Users.Status.INACTIVE);
        }

        userRepository.save(user);
    }

    public void resetFailedLoginAttempts(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        Users user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + normalizedEmail));

        user.setFailedLoginAttempts(0);
        userRepository.save(user);
    }

    public void generateAndSendActivationToken(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        Users user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + normalizedEmail));

        String token = String.format("%04d", new Random().nextInt(10000));
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        user.setActivationToken(token);
        user.setTokenExpiry(expiry);
        userRepository.save(user);

        String validationLink = "http://localhost:4200/validate-token?email=" + normalizedEmail + "&token=" + token;

        String subject = "Your Activation Token";
        String body = String.format(
                "Dear %s,\n\n" +
                        "Your activation token is: %s\n\n" +
                        "Click the link below to validate your token:\n" +
                        "%s\n\n" +
                        "Please note that the token will expire in 5 minutes.\n\n" +
                        "Best regards,\nBloodBank Team",
                user.getFirstName() != null ? user.getFirstName() : "User",
                token,
                validationLink
        );

        emailService.sendEmail(email, subject, body);
    }

    public Map<String, Object> validateActivationToken(String email, String token) {
        String normalizedEmail = email.trim().toLowerCase();

        Users user = userRepository.findByEmailAndActivationToken(normalizedEmail, token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (user.getTokenExpiry() == null || user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired.");
        }

        user.setStatus(Users.Status.ACTIVE);
        user.setActivationToken(null);
        user.setTokenExpiry(null);
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("email", user.getEmail());
        response.put("userId", user.getUserId());

        return response;
    }

    public Users authenticate(String email, String password) {
        String normalizedEmail = email.trim().toLowerCase();
        Users user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (user.getStatus() == Users.Status.INACTIVE) {
            throw new RuntimeException("Account is locked. Please reset your password.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            incrementFailedLogin(normalizedEmail);
            throw new RuntimeException("Invalid email or password");
        }

        resetFailedLoginAttempts(normalizedEmail);

        return user;
    }

    public Users getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    public void updateUserStatus(Long userId, String newStatus) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"ACTIVE".equalsIgnoreCase(newStatus) && !"INACTIVE".equalsIgnoreCase(newStatus)) {
            throw new IllegalArgumentException("Invalid status value. Only ACTIVE or INACTIVE are allowed.");
        }

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

    public void generateForgotPasswordToken(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        Users user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + normalizedEmail));

        String token = String.format("%04d", new Random().nextInt(10000));
        user.setActivationToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusDays(1));
        userRepository.save(user);

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

    public void resetPasswordWithToken(String email, String token, String newPassword) {
        String normalizedEmail = email.trim().toLowerCase();
        Users user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + normalizedEmail));

        if (user.getActivationToken() == null || !user.getActivationToken().equals(token)) {
            throw new RuntimeException("Invalid token.");
        }

        if (user.getTokenExpiry() == null || user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setActivationToken(null);
        user.setTokenExpiry(null);
        userRepository.save(user);
    }

    @Transactional
    public void updateProfile(ProfileUpdateRequestDTO profileRequest) {
        Users user = userRepository.findById(profileRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + profileRequest.getUserId()));

        user.setFirstName(profileRequest.getFirstName());
        user.setLastName(profileRequest.getLastName());
        user.setBloodGroup(profileRequest.getBloodGroup());
        user.setAutoReminders(profileRequest.isAutoReminders());
        user.setNationalId(profileRequest.getNationalId());
        System.out.println("Saving user with updated profile: " + user);
        userRepository.save(user);
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

    private UserDTO mapToDTO(Users user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setGender(user.getGender() != null ? user.getGender().name() : null);
        dto.setNationalId(user.getNationalId());
        dto.setBloodGroup(user.getBloodGroup());
        dto.setEligibilityStatus(user.isEligibilityStatus());
        dto.setStatus(user.getStatus() != null ? user.getStatus().name() : null);
        dto.setAutoReminders(user.isAutoReminders());
        return dto;
    }

    private Users mapToEntity(UserDTO dto) {
        Users user = new Users();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setGender(dto.getGender() != null ? Users.Gender.valueOf(dto.getGender().toUpperCase()) : null);
        user.setNationalId(dto.getNationalId());
        user.setBloodGroup(dto.getBloodGroup());
        user.setEligibilityStatus(dto.isEligibilityStatus());
        user.setStatus(dto.getStatus() != null ? Users.Status.valueOf(dto.getStatus().toUpperCase()) : null);
        user.setAutoReminders(dto.isAutoReminders());
        return user;
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    @Autowired
    private EmailService emailService;
}

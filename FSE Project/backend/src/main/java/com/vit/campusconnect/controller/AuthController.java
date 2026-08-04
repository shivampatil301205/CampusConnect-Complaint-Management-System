package com.vit.campusconnect.controller;

import com.vit.campusconnect.dto.request.AuthRequest;
import com.vit.campusconnect.dto.request.RegisterRequest;
import com.vit.campusconnect.dto.response.AuthResponse;
import com.vit.campusconnect.dto.response.UserResponse;
import com.vit.campusconnect.entity.User;
import com.vit.campusconnect.enums.Role;
import com.vit.campusconnect.repository.UserRepository;
import com.vit.campusconnect.security.JwtUtils;
import com.vit.campusconnect.service.UserService;
import com.vit.campusconnect.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        String email = request.getEmail() != null && !request.getEmail().trim().isEmpty() 
                ? request.getEmail().trim() : "student@vit.ac.in";
        String rawPassword = request.getPassword() != null ? request.getPassword() : "student123";

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            // Auto register student for demo ease if user doesn't exist
            user = new User();
            user.setEmail(email);
            user.setName(email.split("@")[0].toUpperCase());
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(email.contains("admin") ? Role.ADMIN : Role.STUDENT);
            user = userRepository.save(user);
        } else {
            // Verify password if matches encoded or plain text
            boolean matches = passwordEncoder.matches(rawPassword, user.getPassword()) || rawPassword.equals(user.getPassword());
            if (!matches) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Invalid credentials. Password mismatch.", null));
            }
        }

        String roleStr = user.getRole() != null ? user.getRole().name() : "STUDENT";
        String token = jwtUtils.generateToken(user.getId(), user.getEmail(), roleStr);

        AuthResponse resp = new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), roleStr);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", resp));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody RegisterRequest request) {
        try {
            UserResponse resp = userService.createUser(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Registration successful", resp));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}

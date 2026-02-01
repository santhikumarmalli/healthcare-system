package com.healthcare.controller;

import com.healthcare.dto.AuthDTO;
import com.healthcare.entity.User;
import com.healthcare.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and user management APIs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register a new user with role (PATIENT, DOCTOR, ADMIN)")
    public ResponseEntity<AuthDTO.AuthResponse> register(@Valid @RequestBody AuthDTO.RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<AuthDTO.AuthResponse> login(@Valid @RequestBody AuthDTO.LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/profile")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get user profile", description = "Get authenticated user's profile")
    public ResponseEntity<AuthDTO.UserResponse> getProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(authService.getUserProfile(email));
    }

    @PutMapping("/change-password")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Change password", description = "Change current user's password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody AuthDTO.ChangePasswordRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();
        authService.changePassword(email, request);
        return ResponseEntity.ok("Password changed successfully");
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get all users", description = "Get all users (Admin only)")
    public ResponseEntity<List<AuthDTO.UserResponse>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @GetMapping("/users/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get users by role", description = "Get all users with specific role (Admin only)")
    public ResponseEntity<List<AuthDTO.UserResponse>> getUsersByRole(@PathVariable String role) {
        User.Role userRole = User.Role.valueOf(role.toUpperCase());
        return ResponseEntity.ok(authService.getUsersByRole(userRole));
    }

    @PutMapping("/users/{userId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Deactivate user", description = "Deactivate user account (Admin only)")
    public ResponseEntity<String> deactivateUser(@PathVariable Long userId) {
        authService.deactivateUser(userId);
        return ResponseEntity.ok("User deactivated successfully");
    }

    @PutMapping("/users/{userId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Activate user", description = "Activate user account (Admin only)")
    public ResponseEntity<String> activateUser(@PathVariable Long userId) {
        authService.activateUser(userId);
        return ResponseEntity.ok("User activated successfully");
    }
}

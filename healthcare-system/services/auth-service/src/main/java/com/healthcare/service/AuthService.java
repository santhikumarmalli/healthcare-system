package com.healthcare.service;

import com.healthcare.dto.AuthDTO;
import com.healthcare.entity.User;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.exception.BadRequestException;
import com.healthcare.repository.UserRepository;
import com.healthcare.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthDTO.AuthResponse register(AuthDTO.RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(User.Role.valueOf(request.getRole().toUpperCase()))
                .active(true)
                .emailVerified(false)
                .build();

        User savedUser = userRepository.save(user);
        String token = jwtTokenProvider.generateToken(savedUser.getEmail(), savedUser.getRole().name());

        log.info("User registered successfully with ID: {}", savedUser.getId());

        return AuthDTO.AuthResponse.builder()
                .token(token)
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .role(savedUser.getRole())
                .active(savedUser.getActive())
                .build();
    }

    public AuthDTO.AuthResponse login(AuthDTO.LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getActive()) {
            throw new BadRequestException("Account is deactivated");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());

        log.info("User logged in successfully: {}", user.getEmail());

        return AuthDTO.AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .active(user.getActive())
                .build();
    }

    public AuthDTO.UserResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToUserResponse(user);
    }

    @Transactional
    public void changePassword(String email, AuthDTO.ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        log.info("Password changed successfully for user: {}", email);
    }

    public List<AuthDTO.UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public List<AuthDTO.UserResponse> getUsersByRole(User.Role role) {
        return userRepository.findByRole(role).stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(false);
        userRepository.save(user);
        log.info("User deactivated: {}", userId);
    }

    @Transactional
    public void activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(true);
        userRepository.save(user);
        log.info("User activated: {}", userId);
    }

    private AuthDTO.UserResponse mapToUserResponse(User user) {
        return AuthDTO.UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .active(user.getActive())
                .profileImage(user.getProfileImage())
                .build();
    }
}

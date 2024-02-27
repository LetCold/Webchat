package com.phamthainguyen.webchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phamthainguyen.webchat.models.entity.User;
import com.phamthainguyen.webchat.models.entity.User.Role;
import com.phamthainguyen.webchat.models.request.LoginRequest;
import com.phamthainguyen.webchat.models.request.RegisterRequest;
import com.phamthainguyen.webchat.models.response.AuthResponse;
import com.phamthainguyen.webchat.responsitory.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userResponsitory;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByEmail(String email) {
        return userResponsitory.findByEmail(email).orElse(null);
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            return AuthResponse.builder().type("Account").msg("Lỗi đăng nhập").build();
        }
        User user = getUserByEmail(request.getEmail());
        if (user == null) {
            return AuthResponse.builder().type("email").msg("Email does not exist").build();
        }
        String token = jwtService.generateToken(user);
        return AuthResponse.builder().type("login").msg("success").token(token).fullname(user.getFullName()).build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userResponsitory.existsByEmail(request.getEmail())) {
            return AuthResponse.builder().type("Email").msg("Email already exists").build();
        }
        if (request.getPassword().equals(request.getRePassword())) {
            return AuthResponse.builder().type("Password").msg("Passwords do not match").build();
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .gender(request.getGender())
                .idFriend("[]")
                .idRequestFriend("[]")
                .role(Role.USER)
                .build();
        userResponsitory.save(user);
        String token = jwtService.generateToken(user);
        return AuthResponse.builder().type("login").msg("success").token(token).fullname(user.getFullName()).build();
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null
                && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }

    public User getUserByToken(String jwt) {
        String userEmail = jwtService.extractEmail(jwt);
        if (userEmail != null) {
            User user = userResponsitory.findByEmail(userEmail).orElse(null);
            return user;
        }
        return null;
    }

}

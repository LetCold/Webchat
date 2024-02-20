package com.phamthainguyen.webchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public boolean isEmail(String email) {
        return userResponsitory.findByEmail(email) != null;
    }

    public AuthResponse login(LoginRequest request) {
        System.out.println("password " +request.getPassword());
        try{
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }catch(Exception e){
            return AuthResponse.builder().type("Account").msg("Lỗi đăng nhập").build();
        }
        if (!isEmail(request.getEmail())) {
            return AuthResponse.builder().type("email").msg("Email does not exist").build();
        }
        User user = userResponsitory.findByEmail(request.getEmail());
        String token = jwtService.generateToken(user);
        return AuthResponse.builder().type("login").msg("success").token(token).build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (isEmail(request.getEmail())) {
            return AuthResponse.builder().type("email").msg("Email already exists").build();
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .gender(request.getGender())
                .role(Role.USER)
                .build();
        userResponsitory.save(user);
        String token = jwtService.generateToken(user);
        return AuthResponse.builder().type("login").msg("success").token(token).build();
    }

}

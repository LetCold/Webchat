package com.phamthainguyen.webchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phamthainguyen.webchat.models.request.LoginRequest;
import com.phamthainguyen.webchat.models.request.RegisterRequest;
import com.phamthainguyen.webchat.models.response.AuthResponse;
import com.phamthainguyen.webchat.models.response.StatusResponse;
import com.phamthainguyen.webchat.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponse posRegister(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    private AuthResponse postLogin(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/logout")
    public StatusResponse postLogout() {
        SecurityContextHolder.clearContext();
        return StatusResponse.builder().status("success").build();
    }
}

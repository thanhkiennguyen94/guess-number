package com.example.guessnumberservice.service;

import com.example.guessnumberservice.request.LoginRequest;
import com.example.guessnumberservice.request.RegisterRequest;
import com.example.guessnumberservice.response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}

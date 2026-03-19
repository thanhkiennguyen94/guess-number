package com.example.guessnumberservice.controller;

import com.example.guessnumberservice.request.LoginRequest;
import com.example.guessnumberservice.request.RegisterRequest;
import com.example.guessnumberservice.response.ApiResponse;
import com.example.guessnumberservice.response.AuthResponse;
import com.example.guessnumberservice.service.AuthService;
import com.example.guessnumberservice.util.ConstUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ApiResponse.success(null, ConstUtils.REGISTER_SUCCESSFULLY);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }
}

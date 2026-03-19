package com.example.guessnumberservice.service.impl;


import com.example.guessnumberservice.model.User;
import com.example.guessnumberservice.repository.UserRepository;
import com.example.guessnumberservice.request.LoginRequest;
import com.example.guessnumberservice.request.RegisterRequest;
import com.example.guessnumberservice.response.AuthResponse;
import com.example.guessnumberservice.security.jwt.JwtUtil;
import com.example.guessnumberservice.service.AuthService;
import com.example.guessnumberservice.util.ConstMessageError;
import com.example.guessnumberservice.util.ConstantUtils;
import com.example.guessnumberservice.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageUtil messageUtil;
    private final JwtUtil jwtUtil;


    @Override
    public void register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setScore(0);
        user.setTurns(5);

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->  new BadCredentialsException(messageUtil.getMessage(ConstMessageError.LOGIN_INVALID)));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(messageUtil.getMessage(ConstMessageError.LOGIN_INVALID));
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(token);
    }
}

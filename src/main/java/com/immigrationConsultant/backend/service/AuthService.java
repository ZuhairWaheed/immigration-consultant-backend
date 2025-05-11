package com.immigrationConsultant.backend.service;

import com.immigrationConsultant.backend.dto.AuthResponse;
import com.immigrationConsultant.backend.dto.LoginRequest;
import com.immigrationConsultant.backend.dto.RegisterRequest;
import com.immigrationConsultant.backend.model.User;
import com.immigrationConsultant.backend.repository.UserRepository;
import com.immigrationConsultant.backend.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
   // private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository repo, JwtUtil jwtUtil) {
        this.userRepository = repo;
        //this.passwordEncoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        //user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }
}

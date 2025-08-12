package com.gspann.meetingroombooking.service.impl;

import com.gspann.meetingroombooking.dto.AuthResponse;
import com.gspann.meetingroombooking.dto.LoginRequest;
import com.gspann.meetingroombooking.dto.SignupRequest;
import com.gspann.meetingroombooking.entity.User;
import com.gspann.meetingroombooking.repository.UserRepository;
import com.gspann.meetingroombooking.security.JWTTokenProvider;
import com.gspann.meetingroombooking.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Override
    public void register(SignupRequest request) {
        Optional<User> existing=userRepository.findByEmail(request.getEmail());
        if(existing.isPresent()){
            throw new RuntimeException("User already exists");
        }

        User user=new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.EMPLOYEE);
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user=userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new RuntimeException("Invalid email or password");
        String token = jwtTokenProvider.generateToken(user.getId(),user.getEmail(),user.getRole().name());

        return new AuthResponse(token);

    }
}

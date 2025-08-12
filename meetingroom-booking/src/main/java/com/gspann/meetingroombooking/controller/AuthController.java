package com.gspann.meetingroombooking.controller;

import com.gspann.meetingroombooking.dto.AuthResponse;
import com.gspann.meetingroombooking.dto.LoginRequest;
import com.gspann.meetingroombooking.dto.SignupRequest;
import com.gspann.meetingroombooking.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        authService.register(signupRequest);
        return ResponseEntity.ok().body("User Registered Succesfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        AuthResponse response =authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

}

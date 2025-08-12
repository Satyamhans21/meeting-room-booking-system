package com.gspann.meetingroombooking.service;

import com.gspann.meetingroombooking.dto.AuthResponse;
import com.gspann.meetingroombooking.dto.LoginRequest;
import com.gspann.meetingroombooking.dto.SignupRequest;

public interface AuthService {
    void register(SignupRequest request);
    AuthResponse login(LoginRequest request);
}

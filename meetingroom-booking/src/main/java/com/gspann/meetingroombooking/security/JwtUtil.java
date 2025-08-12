package com.gspann.meetingroombooking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    public String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // remove "Bearer "
        }
        return null;
    }

    public Long extractUserId(String token) {
        if (token == null) {
            throw new IllegalArgumentException("JWT token is missing");
        }

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtSecret))
                .build()
                .verify(token);

        // Assuming you set userId as a claim (like: .withClaim("userId", user.getId()))
        return decodedJWT.getClaim("userId").asLong();
    }
}

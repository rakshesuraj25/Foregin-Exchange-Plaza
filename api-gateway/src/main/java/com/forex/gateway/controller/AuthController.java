package com.forex.gateway.controller;

import com.forex.gateway.security.JwtUtil; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // POST: http://localhost:8765/auth/login
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest request) {
        // ✅ Simple demo authentication (accepts any credentials)
        // In real apps, validate user from DB or Auth Service
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body("Username is required"));
        }

        // Generate JWT token for given username
        String token = JwtUtil.generateToken(request.getUsername());
        return Mono.just(ResponseEntity.ok(token));
    }
}

package com.pawsstore.shop.controller;

import com.pawsstore.shop.dto.AuthRequest;
import com.pawsstore.shop.dto.AuthResponse;
import com.pawsstore.shop.dto.RefreshRequest;
import com.pawsstore.shop.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = tokenService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/refresh")
    public ResponseEntity<AuthResponse> refreshAccessToken(@RequestBody RefreshRequest request) {
        AuthResponse response = tokenService.refresh(request);
        return ResponseEntity.ok(response);
    }
}

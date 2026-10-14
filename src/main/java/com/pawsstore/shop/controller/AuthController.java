package com.pawsstore.shop.controller;

import com.pawsstore.shop.dto.auth.AuthRequest;
import com.pawsstore.shop.dto.auth.AuthResponse;
import com.pawsstore.shop.dto.RefreshRequest;
import com.pawsstore.shop.dto.auth.LogoutRequest;
import com.pawsstore.shop.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = tokenService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/refresh")
    public ResponseEntity<AuthResponse> refreshAccessToken(@RequestBody @Valid RefreshRequest request) {
        AuthResponse response = tokenService.refresh(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody @Valid LogoutRequest request) {
        tokenService.revoke(request);
        return ResponseEntity.status(204).build();
    }
}

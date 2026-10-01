package com.pawsstore.shop.controller;

import com.pawsstore.shop.dto.TokenRequest;
import com.pawsstore.shop.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> getToken(@RequestBody TokenRequest request) {
        return ResponseEntity.ok().body(tokenService.login(request));
    }

}

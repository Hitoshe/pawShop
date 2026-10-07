package com.pawsstore.shop.dto.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}

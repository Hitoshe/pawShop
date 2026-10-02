package com.pawsstore.shop.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}

package com.pawsstore.shop.dto.auth;

public record AuthRequest(
        String email,
        String password
) {
}

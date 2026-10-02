package com.pawsstore.shop.dto;

public record AuthRequest(
        String email,
        String password
) {
}

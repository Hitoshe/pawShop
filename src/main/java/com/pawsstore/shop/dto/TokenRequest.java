package com.pawsstore.shop.dto;

public record TokenRequest(
        String email,
        String password
) {
}

package com.pawsstore.shop.dto.auth;

import jakarta.validation.constraints.NotNull;

public record LogoutRequest(
        @NotNull String refreshToken
) {
}

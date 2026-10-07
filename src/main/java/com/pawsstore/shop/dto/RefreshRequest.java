package com.pawsstore.shop.dto;

import jakarta.validation.constraints.NotNull;

public record RefreshRequest(
        @NotNull String refreshToken
) {
}

package com.pawsstore.shop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotBlank
        @Email
        String email,
        @NotNull String password,
        @NotNull String role
) {
}

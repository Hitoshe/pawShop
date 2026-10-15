package com.pawsstore.shop.dto.cart;

import jakarta.validation.constraints.NotBlank;

public record ApplyPromoRequest(
        @NotBlank String code
) {
}

package com.pawsstore.shop.dto;

import java.math.BigDecimal;

public record ProductProjection(
        String title,
        BigDecimal price,
        BigDecimal rating
) {
}

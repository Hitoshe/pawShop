package com.pawsstore.shop.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        Long id,
        String title,
        String description,
        BigDecimal price,
        BigDecimal rating,
        List<String> images,
        Long stockQuantity,
        LocalDateTime createdAt
) {
}

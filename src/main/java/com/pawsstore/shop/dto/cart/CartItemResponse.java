package com.pawsstore.shop.dto.cart;

import java.math.BigDecimal;

public record CartItemResponse(
        Long id,
        Long productId,
        String title,
        BigDecimal price,
        Integer quantity,
        BigDecimal lineTotal
) {
}

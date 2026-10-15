package com.pawsstore.shop.dto.cart;

import java.math.BigDecimal;

public record PromoApplicationResponse(
        BigDecimal subtotal,
        BigDecimal discountPercentage,
        BigDecimal discountAmount,
        BigDecimal total
) {
}

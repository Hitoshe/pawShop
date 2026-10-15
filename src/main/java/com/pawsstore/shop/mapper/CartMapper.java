package com.pawsstore.shop.mapper;

import com.pawsstore.shop.dto.cart.CartItemResponse;
import com.pawsstore.shop.dto.cart.CartResponse;
import com.pawsstore.shop.model.Cart;
import com.pawsstore.shop.model.CartItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class CartMapper {

    private static final int MONEY_SCALE = 2;

    private CartMapper() {
    }

    public static CartResponse toResponse(Cart cart, List<CartItem> items) {
        List<CartItemResponse> lines = items.stream()
                .map(CartMapper::toItem)
                .toList();
        BigDecimal subtotal = lines.stream()
                .map(CartItemResponse::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartResponse(cart.getId(), lines, money(subtotal));
    }

    public static BigDecimal money(BigDecimal value) {
        return value.setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    private static CartItemResponse toItem(CartItem item) {
        BigDecimal price = money(item.getProduct().getPrice());
        BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(item.getQuantity()));
        return new CartItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getTitle(),
                price,
                item.getQuantity(),
                money(lineTotal)
        );
    }
}

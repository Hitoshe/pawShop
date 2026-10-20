package com.pawsstore.shop.dto.product;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record UpdateProductRequest(
        @Size(min = 1, max = 100) String title,
        @Size(min = 1, max = 2000) String description,
        @DecimalMin("0.01") BigDecimal price,
        @DecimalMin("0.00") @DecimalMax("5.00") BigDecimal rating,
        @Size(min = 3) List<String> images,
        @Min(0) Long stockQuantity
) {
}

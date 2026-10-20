package com.pawsstore.shop.dto.product;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductRequest(
        @NotBlank @Size(max = 100) String title,
        @NotBlank @Size(max = 2000) String description,
        @NotNull @DecimalMin("0.01") BigDecimal price,
        @DecimalMin("0.00") @DecimalMax("5.00") BigDecimal rating,
        @NotNull @Size(min = 3) List<String> images,
        @NotNull @Min(0) Long stockQuantity
) {
}

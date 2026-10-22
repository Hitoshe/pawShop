package com.pawsstore.shop.mapper;

import com.pawsstore.shop.dto.product.ProductResponse;
import com.pawsstore.shop.model.Product;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getRating(),
                product.getImages(),
                product.getStockQuantity(),
                product.getCreatedAt()
        );
    }
}

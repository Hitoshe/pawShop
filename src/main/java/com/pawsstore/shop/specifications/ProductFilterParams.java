package com.pawsstore.shop.specifications;

import org.springframework.web.bind.annotation.BindParam;

import java.math.BigDecimal;

public record ProductFilterParams(
        @BindParam("search") String search,
        @BindParam("min_price") BigDecimal minPrice,
        @BindParam("max_price") BigDecimal maxPrice,
        @BindParam("min_rating") BigDecimal minRating
) {


}

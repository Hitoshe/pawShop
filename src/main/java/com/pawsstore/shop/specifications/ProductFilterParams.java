package com.pawsstore.shop.specifications;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.BindParam;

import java.math.BigDecimal;

public record ProductFilterParams(
        @BindParam("page") Integer page,
        @BindParam("sort_by") String sortBy,
        @BindParam("order") String order,
        @BindParam("limit") Integer size,
        @BindParam("search") String search,
        @BindParam("min_price") BigDecimal minPrice,
        @BindParam("max_price") BigDecimal maxPrice,
        @BindParam("min_rating") BigDecimal minRating
) {
    public Pageable toPageable() {

        int pageSize = size == null ? 12 : Math.max(12, size);

        int pageNumber = page == null ? 0 : Math.max(page - 1, 0);

        String field = switch (sortBy != null ? sortBy : "createdAt") {
            case "price" -> "price";
            case "title" -> "title";
            default -> "createdAt";
        };

        Sort.Direction direction = "desc".equalsIgnoreCase(order)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, field);

        return PageRequest.of(pageNumber, pageSize, sort);
    }

    @Override
    public String toString() {
        return
                "page=" + page +
                "&sort_by=" + sortBy +
                "&order=" + order +
                "&size=" + size +
                "&search=" + search  +
                "&min_price=" + minPrice +
                "&max_price=" + maxPrice +
                "&min_rating=" + minRating;
    }
}

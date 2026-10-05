package com.pawsstore.shop.specifications;

import com.pawsstore.shop.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;


public class ProductSpecification {

    private ProductSpecification() {

    }

    public static Specification<Product> hasTitle(String title) {
        if (title == null || title.isBlank()) {
            return Specification.unrestricted();
        }
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + title.toLowerCase() + "%"))
                        );
    }

    public static Specification<Product> hasPriceBetween(BigDecimal min, BigDecimal max) {

        if (min == null && max == null) {
            return Specification.unrestricted();
        }

        return ((root, query, criteriaBuilder) -> {
            if(min != null && max != null) {
                return criteriaBuilder.between(root.get("price"), min, max);
            }
            if(min != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min);
            }
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), max);
        });
    }

    public static Specification<Product> hasRatingHigherThan(BigDecimal rating) {
            if(rating == null) {
                return Specification.unrestricted();
            }
        return ((root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating)
        );
    }

}

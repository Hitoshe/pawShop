package com.pawsstore.shop.repository;

import com.pawsstore.shop.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Кастомный запрос на языке JPQL.
     * Позволяет фильтровать товары по диапазону цен и минимальному рейтингу одновременно.
     */
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice " +
            "AND (:applyRating = false OR FLOOR(p.rating) IN :ratings)")
    List<Product> filterProducts(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("ratings") List<Integer> ratings,
            @Param("applyRating") boolean applyRating,
            Sort sort
    );
}
package com.pawsstore.shop.repository;

import com.pawsstore.shop.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Поиск всех отзывов, принадлежащих конкретному товару.
     * Spring Data JPA автоматически создаст SQL запрос: SELECT * FROM review WHERE product_id = ?
     */
    List<Review> findByProductId(Long productId);
}
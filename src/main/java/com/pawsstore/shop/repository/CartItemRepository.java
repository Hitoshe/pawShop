package com.pawsstore.shop.repository;

import com.pawsstore.shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    @Query("SELECT item FROM CartItem item JOIN FETCH item.product WHERE item.cart.id = :cartId")
    List<CartItem> findAllWithProductByCartId(@Param("cartId") Long cartId);

    @Query("""
            SELECT item FROM CartItem item
            JOIN FETCH item.cart cart
            JOIN FETCH cart.user
            JOIN FETCH item.product
            WHERE item.id = :id
            """)
    Optional<CartItem> findWithOwnerById(@Param("id") Long id);
}

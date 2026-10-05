package com.pawsstore.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "rating")
    private BigDecimal rating;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "images", nullable = false, columnDefinition = "jsonb")
    private List<String> images;

    @Column(name = "stock_quantity", nullable = false)
    private Long stockQuantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
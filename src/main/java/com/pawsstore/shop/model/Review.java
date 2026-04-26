package com.pawsstore.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId; // Внешний ключ (связь с Product)
    private String author;
    private Integer rating;

    @Column(length = 1000)
    private String comment;

    private LocalDate date = LocalDate.now(); // Дата создания по умолчанию - сегодня
}
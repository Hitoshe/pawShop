package com.pawsstore.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Класс-сущность товара.
 * Hibernate автоматически создаст таблицу "products" в БД на основе этого класса.
 */
@Entity
@Table(name = "products")
@Data // Lombok сам создаст геттеры, сеттеры и toString
@NoArgsConstructor // Пустой конструктор
@AllArgsConstructor // Конструктор со всеми полями
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // Название товара
    private String description; // Описание
    private Double price;       // Цена
    private Double rating;      // Рейтинг (звездочки)
    private Integer reviews;    // Количество отзывов
    private String imageUrl;    // Путь к картинке
    private String category;    // Категория (для фильтров)
}
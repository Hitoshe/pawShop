package com.pawsstore.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Сущность товара.
 * Поля дублируются (Ru/En) для обеспечения мультиязычности без использования сложных словарей.
 */
@Entity
@Table(name = "products")
@Data // Генерирует геттеры, сеттеры, toString и equals/hashCode через Lombok
@NoArgsConstructor // Пустой конструктор (нужен для JPA)
@AllArgsConstructor // Конструктор со всеми полями (используется в DataInitializer)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Текстовые данные на двух языках
    private String nameEn;
    private String nameRu;
    private String categoryEn;
    private String categoryRu;

    @Column(length = 500)
    private String shortDescriptionEn;
    @Column(length = 500)
    private String shortDescriptionRu;

    @Column(length = 2000)
    private String fullDescriptionEn;
    @Column(length = 2000)
    private String fullDescriptionRu;

    // Числовые данные
    private Double price;
    private Double oldPrice;
    private Double rating;
    private Integer reviewsCount;

    // Ссылки на изображения (хранятся в /static/images/)
    private String imageUrl;
    private String imageUrl2;
    private String imageUrl3;

    // Технические характеристики
    private String materialEn;
    private String materialRu;
    private String dimensionsEn;
    private String dimensionsRu;
    private String weightLimitEn;
    private String weightLimitRu;
}
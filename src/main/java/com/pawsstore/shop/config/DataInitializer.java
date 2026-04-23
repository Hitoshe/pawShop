package com.pawsstore.shop.config;

import com.pawsstore.shop.model.Product;
import com.pawsstore.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            // Если в базе еще нет товаров — добавляем их
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Product(null, "Airline-Approved Pet Travel Carrier", "Soft-sided pet carrier...", 54.99, 4.0, 4, "/images/carrier.png", "Travel"),
                        new Product(null, "Interactive Rubber Chew Ball", "Durable ball for dogs", 15.99, 5.0, 5, "/images/ball.png", "Toys"),
                        new Product(null, "Multi-Level Cat Scratching Post", "Fun for your cat", 89.99, 4.5, 12, "/images/cat-tree.png", "Home")
                ));
                System.out.println(">>> Данные успешно загружены в БД!");
            }
        };
    }
}
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
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Product(null, "Airline-Approved Pet Travel Carrier", "Soft-sided pet carrier for airline cabin use.", 54.99, 4.0, 4, "/images/AirlineApprovedPetTravelCarrier.png", "Travel"),
                        new Product(null, "Interactive Rubber Chew Ball", "Durable rubber ball for active dogs.", 15.99, 5.0, 5, "/images/InteractiveRubberChewBall.png", "Toys"),
                        new Product(null, "Multi-Level Cat Scratching Post", "Fun and durable scratching post for cats.", 89.99, 4.5, 12, "/images/Multi-LevelCatScratchingPost.png", "Cats"),
                        new Product(null, "Orthopedic Memory Foam Pet Bed", "Premium comfort for your senior pet.", 69.99, 3.5, 8, "/images/OrthopedicMemoryFoamPetBed.png", "Beds"),
                        new Product(null, "Premium Stainless Steel Dog Bowl", "Non-slip and easy to clean.", 24.99, 5.0, 10, "/images/PremiumStainlessSteelDogBowl.png", "Feeding"),
                        new Product(null, "Reflective Nylon Dog Leash & Collar Set", "Safety and style for night walks.", 32.99, 4.0, 6, "/images/ReflectiveNylonDogLeash&CollarSet.png", "Walking")
                ));
                System.out.println(">>> Данные обновлены согласно макету!");
            }
        };
    }
}
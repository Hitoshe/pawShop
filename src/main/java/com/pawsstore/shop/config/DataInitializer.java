package com.pawsstore.shop.config;

import com.pawsstore.shop.model.Product;
import com.pawsstore.shop.model.Review;
import com.pawsstore.shop.repository.ProductRepository;
import com.pawsstore.shop.repository.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration // Помечает класс как источник определений бинов для контекста Spring
public class DataInitializer {

    /**
     * Bean CommandLineRunner запускается автоматически сразу после старта приложения.
     * Используется для первичного наполнения базы данных (Seeding).
     */
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepo, ReviewRepository reviewRepo) {
        return args -> {
            // Проверка: если в базе уже есть товары, ничего не делаем, чтобы избежать дублей
            if (productRepo.count() == 0) {

                // 1. Создаем список базовых товаров
                // Конструктор Product принимает 22 параметра (названия, описания, цены, фото и хар-ки на двух языках)
                List<Product> products = new ArrayList<>(List.of(
                        // Группа товаров со скидкой (указан oldPrice)
                        new Product(null, "Airline-Approved Pet Travel Carrier", "Сумка-переноска для животных", "Travel", "Путешествия", "Perfect for flights.", "Идеально для полетoв.", "Soft-sided pet carrier approved for airline cabin use.", "Мягкая сумка-переноска для салона самолета.",
                                54.99, 75.00, 0.0, 0, "/images/AirlineApprovedPetTravelCarrier.png", "/images/AirlineApprovedPetTravelCarrier1.png", "/images/AirlineApprovedPetTravelCarrier2.png",
                                "Polyester", "Полиэстер", "18x11x11 in", "45x28x28 см", "Up to 15 lbs", "До 7 кг"),

                        new Product(null, "Interactive Rubber Chew Ball", "Интерактивный резиновый мяч", "Toys", "Игрушки", "Durable toy.", "Прочная игрушка.", "Durable rubber ball for active dogs.", "Прочный резиновый мяч для активных собак.",
                                15.99, 22.00, 0.0, 0, "/images/InteractiveRubberChewBall.png", "/images/InteractiveRubberChewBall1.png", "/images/InteractiveRubberChewBall2.png",
                                "Rubber", "Резина", "3 inch", "7.5 см", "N/A", "Н/Д"),

                        new Product(null, "Multi-Level Cat Scratching Post", "Многоуровневая когтеточка", "Cats", "Кошки", "Fun for cats.", "Радость для кошек.", "Spacious cat tree with scratching posts.", "Просторный комплекс с когтеточками.",
                                89.99, 110.0, 0.0, 0, "/images/Multi-LevelCatScratchingPost.png", "/images/Multi-LevelCatScratchingPost1.png", "/images/Multi-LevelCatScratchingPost2.png",
                                "Sisal Rope", "Сизалевый канат", "24x16x32 in", "60x40x80 см", "Up to 20 lbs", "До 9 кг"),

                        // Группа товаров по полной цене (oldPrice = null)
                        new Product(null, "Orthopedic Memory Foam Pet Bed", "Ортопедическая лежанка", "Beds", "Лежанки", "Premium comfort.", "Премиальный комфорт.", "Supports joints with high-density foam.", "Поддержка суставов благодаря пене с памятью.",
                                69.99, null, 0.0, 0, "/images/OrthopedicMemoryFoamPetBed.png", "/images/OrthopedicMemoryFoamPetBed1.png", "/images/OrthopedicMemoryFoamPetBed2.png",
                                "Memory Foam", "Пена с памятью", "36x24x4 in", "90x60x10 см", "Up to 50 lbs", "До 23 кг"),

                        new Product(null, "Premium Stainless Steel Dog Bowl", "Миска из нержавеющей стали", "Feeding", "Кормление", "Easy to clean.", "Легко чистить.", "Double-wall stainless steel bowl.", "Миска из нержавейки с двойными стенками.",
                                24.99, null, 0.0, 0, "/images/PremiumStainlessSteelDogBowl.png", "/images/PremiumStainlessSteelDogBowl1.png", "/images/PremiumStainlessSteelDogBowl2.png",
                                "Stainless Steel", "Нержавейка", "8x8x3 in", "20x20x7 см", "32 oz", "0.9 л"),

                        new Product(null, "Reflective Nylon Dog Leash Set", "Светоотражающий набор", "Walking", "Прогулки", "Safety first.", "Безопасность.", "High-visibility stitching.", "Светоотражающая строчка для ночных прогулок.",
                                32.99, null, 0.0, 0, "/images/ReflectiveNylonDogLeash&CollarSet.png", "/images/ReflectiveNylonDogLeash&CollarSet1.png", "/images/ReflectiveNylonDogLeash&CollarSet2.png",
                                "Nylon", "Нейлон", "5ft", "1.5 м", "N/A", "Н/Д")
                ));

                // Сначала сохраняем товары в БД, чтобы Hibernate присвоил им уникальные ID
                productRepo.saveAll(products);

                // 2. Генерируем случайные отзывы для каждого товара
                Random random = new Random();
                String[] authors = {"Alice", "Bob", "Charlie", "Дмитрий", "Елена", "Рекс", "Max", "Luna"};
                String[] comments = {
                        "Great quality, my pet loves it!",
                        "Fast delivery and good packaging.",
                        "Excellent product for the price.",
                        "Amazing! Will buy again.",
                        "Качество на высоте, рекомендую!",
                        "Очень быстрая доставка, спасибо.",
                        "Моему коту очень понравилось.",
                        "Хороший товар за свои деньги."
                };

                for (Product p : products) {
                    int reviewCount = 2 + random.nextInt(4); // Каждому товару даем от 2 до 5 отзывов
                    double totalRating = 0;

                    for (int i = 0; i < reviewCount; i++) {
                        int rating = 3 + random.nextInt(3); // Оценка от 3 до 5 звезд
                        totalRating += rating;

                        // Сохраняем отзыв, привязывая его к ID товара
                        reviewRepo.save(new Review(null, p.getId(),
                                authors[random.nextInt(authors.length)],
                                rating,
                                comments[random.nextInt(comments.length)],
                                LocalDate.now().minusDays(random.nextInt(30)))); // Дата за последние 30 дней
                    }

                    // 3. Обновляем данные о рейтинге в самом объекте товара
                    p.setRating(totalRating / reviewCount); // Среднее арифметическое
                    p.setReviewsCount(reviewCount);
                }

                // Пересохраняем товары, теперь уже с рассчитанными рейтингами
                productRepo.saveAll(products);
                System.out.println(">>> База успешно наполнена (Товары + Акции + Случайные отзывы)!");
            }
        };
    }
}
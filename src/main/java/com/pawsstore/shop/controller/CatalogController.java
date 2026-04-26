package com.pawsstore.shop.controller;

import com.pawsstore.shop.model.Product;
import com.pawsstore.shop.model.Review;
import com.pawsstore.shop.repository.ProductRepository;
import com.pawsstore.shop.repository.ReviewRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

@Controller
public class CatalogController {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    // Внедрение репозиториев через конструктор
    public CatalogController(ProductRepository productRepository , ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * Главная страница каталога с поддержкой фильтров и сортировки.
     */
    @GetMapping("/")
    public String showCatalog(
            @RequestParam(required = false, defaultValue = "0") Double minPrice,
            @RequestParam(required = false, defaultValue = "1000") Double maxPrice,
            @RequestParam(required = false, defaultValue = "0") Double minRating,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            Model model,
            Locale locale) {

        // Динамический выбор поля для сортировки
        String sortField;
        switch (sortBy.toLowerCase()) {
            case "price":
                sortField = "price";
                break;
            case "rating":
                sortField = "rating";
                break;
            default:
                // Если сортируем по имени, выбираем поле nameRu или nameEn в зависимости от языка сайта
                sortField = locale.getLanguage().equals("ru") ? "nameRu" : "nameEn";
                break;
        }

        // Формируем объект Sort для Spring Data JPA
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        // Выполняем поиск в базе с учетом всех фильтров
        List<Product> products = productRepository.filterProducts(minPrice, maxPrice, minRating, sort);

        model.addAttribute("products", products);
        return "catalog"; // Возврат шаблона catalog.html
    }

    /**
     * Страница одного конкретного товара.
     */
    @GetMapping("/product/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        // Получаем список всех отзывов именно для этого товара
        List<Review> reviews = reviewRepository.findByProductId(id);

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        return "product-detail";
    }

    @GetMapping("/cart")
    public String showCart() { return "cart"; }

    @GetMapping("/categories")
    public String showCategories() { return "categories"; }

    @GetMapping("/about")
    public String showAbout() { return "about"; }

    /**
     * Страница акций: фильтруем товары, у которых есть oldPrice.
     */
    @GetMapping("/deals")
    public String showDeals(Model model) {
        List<Product> dealProducts = productRepository.findAll().stream()
                .filter(p -> p.getOldPrice() != null && p.getOldPrice() > p.getPrice())
                .toList();

        model.addAttribute("products", dealProducts);
        model.addAttribute("isDealsPage", true);
        return "deals";
    }

    /**
     * Обработка отправки формы нового отзыва.
     */
    @PostMapping("/product/{id}/review")
    public String addReview(@PathVariable Long id,
                            @RequestParam String author,
                            @RequestParam Integer rating,
                            @RequestParam String comment) {
        // 1. Создаем и сохраняем новый отзыв
        reviewRepository.save(new Review(null, id, author, rating, comment, java.time.LocalDate.now()));

        // 2. Пересчитываем средний рейтинг товара на лету
        Product product = productRepository.findById(id).orElseThrow();
        List<Review> reviews = reviewRepository.findByProductId(id);
        double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);

        product.setRating(avg);
        product.setReviewsCount(reviews.size());
        productRepository.save(product); // Обновляем данные товара в базе

        return "redirect:/product/" + id; // Перезагружаем страницу товара
    }
}
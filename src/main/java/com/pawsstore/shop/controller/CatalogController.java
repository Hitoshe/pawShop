package com.pawsstore.shop.controller;

import com.pawsstore.shop.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

/**
 * Основной контроллер для отображения страниц каталога.
 */
@Controller
public class CatalogController {

    private final ProductRepository productRepository;

    // Spring сам передаст сюда репозиторий (это и есть Dependency Injection)
    public CatalogController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Аннотация GetMapping говорит Spring, что этот метод сработает при переходе на корень сайта "/"
    @GetMapping("/")
    public String showCatalog(Model model) {
        // Достаем все товары из базы и кладем их в "коробку" под названием model
        // Возвращает имя HTML файла из папки templates (без расширения .html)
        model.addAttribute("products", productRepository.findAll());
        return "catalog";
    }
}
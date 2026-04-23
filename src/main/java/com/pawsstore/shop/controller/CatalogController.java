package com.pawsstore.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Основной контроллер для отображения страниц каталога.
 */
@Controller
public class CatalogController {

    // Аннотация GetMapping говорит Spring, что этот метод сработает при переходе на корень сайта "/"
    @GetMapping("/")
    public String showCatalog() {
        // Возвращает имя HTML файла из папки templates (без расширения .html)
        return "catalog";
    }
}
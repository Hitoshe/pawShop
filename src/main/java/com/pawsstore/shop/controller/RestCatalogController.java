package com.pawsstore.shop.controller;

import com.pawsstore.shop.model.Product;
import com.pawsstore.shop.service.ProductService;
import com.pawsstore.shop.specifications.ProductFilterParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/catalog")
@RequiredArgsConstructor
public class RestCatalogController {

    private final ProductService productService;

    @GetMapping
    public Page<Product> getAllProducts(@ModelAttribute ProductFilterParams params, @PageableDefault Pageable pageable) {
        return productService.getProducts(params, pageable);
    }
}

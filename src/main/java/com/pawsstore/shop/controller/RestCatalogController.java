package com.pawsstore.shop.controller;

import com.pawsstore.shop.dto.PagedResponse;
import com.pawsstore.shop.dto.ProductProjection;
import com.pawsstore.shop.service.ProductService;
import com.pawsstore.shop.specifications.ProductFilterParams;
import lombok.RequiredArgsConstructor;
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
    public PagedResponse<ProductProjection> getAllProducts(@ModelAttribute ProductFilterParams params) {
        System.out.println(params);
        return productService.getProducts(params);
    }
}

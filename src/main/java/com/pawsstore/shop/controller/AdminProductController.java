package com.pawsstore.shop.controller;

import com.pawsstore.shop.dto.product.CreateProductRequest;
import com.pawsstore.shop.dto.product.ProductResponse;
import com.pawsstore.shop.dto.product.UpdateProductRequest;
import com.pawsstore.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "product:params", allEntries = true)
    public ProductResponse create(@RequestBody @Valid CreateProductRequest request) {
        return productService.create(request);
    }

    @PatchMapping("/{id}")
    @CacheEvict(value = "product:params", allEntries = true)
    public ProductResponse update(@PathVariable Long id, @RequestBody @Valid UpdateProductRequest request) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "product:params", allEntries = true)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}

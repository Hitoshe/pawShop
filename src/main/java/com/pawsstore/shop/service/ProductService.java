package com.pawsstore.shop.service;

import com.pawsstore.shop.model.Product;
import com.pawsstore.shop.repository.ProductRepository;
import com.pawsstore.shop.specifications.ProductFilterParams;
import com.pawsstore.shop.specifications.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;


    public Page<Product> getProducts(ProductFilterParams params, Pageable pageable) {
        Specification<Product> productSpecification = Specification.allOf(
                ProductSpecification.hasTitle(params.search()),
                ProductSpecification.hasPriceBetween(params.minPrice(), params.maxPrice()),
                ProductSpecification.hasRatingHigherThan(params.minRating())
        );

        return repository.findAll(productSpecification, pageable);
    }
}

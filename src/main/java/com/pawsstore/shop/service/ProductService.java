package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.PagedResponse;
import com.pawsstore.shop.dto.ProductProjection;
import com.pawsstore.shop.mapper.PageMapper;
import com.pawsstore.shop.model.Product;
import com.pawsstore.shop.repository.ProductRepository;
import com.pawsstore.shop.specifications.ProductFilterParams;
import com.pawsstore.shop.specifications.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;


    public PagedResponse<ProductProjection> getProducts(ProductFilterParams params) {
        Specification<Product> productSpecification = Specification.allOf(
                ProductSpecification.hasTitle(params.search()),
                ProductSpecification.hasPriceBetween(params.minPrice(), params.maxPrice()),
                ProductSpecification.hasRatingHigherThan(params.minRating())
        );

        Page<ProductProjection> page = repository.findBy(productSpecification, q -> q
                .as(ProductProjection.class)
                .page(params.toPageable()));

        return PageMapper.toResponse(page);
    }
}

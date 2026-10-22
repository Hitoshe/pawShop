package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.PagedResponse;
import com.pawsstore.shop.dto.ProductProjection;
import com.pawsstore.shop.dto.product.CreateProductRequest;
import com.pawsstore.shop.dto.product.ProductResponse;
import com.pawsstore.shop.dto.product.UpdateProductRequest;
import com.pawsstore.shop.exceptions.ProductNotFoundException;
import com.pawsstore.shop.mapper.PageMapper;
import com.pawsstore.shop.mapper.ProductMapper;
import com.pawsstore.shop.model.Product;
import com.pawsstore.shop.repository.ProductRepository;
import com.pawsstore.shop.specifications.ProductFilterParams;
import com.pawsstore.shop.specifications.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Cacheable(value = "product:params", key = "#params.toString()")
    public PagedResponse<ProductProjection> getProducts(ProductFilterParams params) {
        Specification<Product> productSpecification = Specification.allOf(
                ProductSpecification.isNotDeleted(),
                ProductSpecification.hasTitle(params.search()),
                ProductSpecification.hasPriceBetween(params.minPrice(), params.maxPrice()),
                ProductSpecification.hasRatingHigherThan(params.minRating())
        );

        Page<ProductProjection> page = repository.findBy(productSpecification, q -> q
                .as(ProductProjection.class)
                .page(params.toPageable()));

        return PageMapper.toResponse(page);
    }

    @Transactional
    @CacheEvict(value = "product:params", allEntries = true)
    public ProductResponse create(CreateProductRequest request) {
        Product product = new Product();
        product.setTitle(request.title());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setRating(request.rating());
        product.setImages(request.images());
        product.setStockQuantity(request.stockQuantity());
        product.setCreatedAt(LocalDateTime.now());
        return ProductMapper.toResponse(repository.save(product));
    }

    @Transactional
    @CacheEvict(value = "product:params", allEntries = true)
    public ProductResponse update(Long id, UpdateProductRequest request) {
        Product product = activeProduct(id);
        if (request.title() != null) {
            product.setTitle(request.title());
        }
        if (request.description() != null) {
            product.setDescription(request.description());
        }
        if (request.price() != null) {
            product.setPrice(request.price());
        }
        if (request.rating() != null) {
            product.setRating(request.rating());
        }
        if (request.images() != null) {
            product.setImages(request.images());
        }
        if (request.stockQuantity() != null) {
            product.setStockQuantity(request.stockQuantity());
        }
        return ProductMapper.toResponse(repository.save(product));
    }

    @Transactional
    @CacheEvict(value = "product:params", allEntries = true)
    public void delete(Long id) {
        Product product = activeProduct(id);
        product.setDeletedAt(LocalDateTime.now());
        repository.save(product);
    }

    private Product activeProduct(Long id) {
        return repository.findById(id)
                .filter(product -> product.getDeletedAt() == null)
                .orElseThrow(() -> new ProductNotFoundException("Товар не найден"));
    }
}

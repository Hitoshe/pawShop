package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.PagedResponse;
import com.pawsstore.shop.dto.ProductProjection;
import com.pawsstore.shop.dto.product.ProductResponse;
import com.pawsstore.shop.dto.product.UpdateProductRequest;
import com.pawsstore.shop.exceptions.ProductNotFoundException;
import com.pawsstore.shop.model.Product;
import com.pawsstore.shop.repository.ProductRepository;
import com.pawsstore.shop.specifications.ProductFilterParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceUnitTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getProducts_WithEmptyFilters() {

        ProductFilterParams productFilterParams =
                new ProductFilterParams(null, null,null,null,null,null,null,null);


        ProductProjection productProjection = new ProductProjection("title", BigDecimal.ZERO, BigDecimal.valueOf(3.5));
        List<ProductProjection> projectionList = List.of(productProjection);


        PageRequest pageRequest = PageRequest.of(0, 1);
        PageImpl<ProductProjection> products = new PageImpl<>(projectionList, pageRequest, 1L);
        Mockito.when(repository.findBy(any(Specification.class), any())).thenReturn(products);

        PagedResponse<ProductProjection> products2 = productService.getProducts(productFilterParams);

        Mockito.verify(repository).findBy(any(Specification.class), any());

        Assertions.assertEquals("title", products2.elements().getFirst().title());
        Assertions.assertEquals(BigDecimal.ZERO, products2.elements().getFirst().price());
        Assertions.assertEquals(BigDecimal.valueOf(3.5), products2.elements().getFirst().rating());

    }

    @Test
    void updateChangesOnlyTheSentPrice() {
        Product product = storedProduct();
        when(repository.findById(5L)).thenReturn(Optional.of(product));
        when(repository.save(product)).thenReturn(product);

        ProductResponse response = productService.update(5L,
                new UpdateProductRequest(null, null, new BigDecimal("20.00"), null, null, null));

        Assertions.assertEquals("Старое", response.title());
        Assertions.assertEquals(new BigDecimal("20.00"), response.price());
    }

    @Test
    void deleteMarksTheProductAndKeepsTheRow() {
        Product product = storedProduct();
        when(repository.findById(5L)).thenReturn(Optional.of(product));

        productService.delete(5L);

        assertNotNull(product.getDeletedAt());
        verify(repository).save(product);
        verify(repository, never()).delete(any(Product.class));
    }

    @Test
    void deleteOfMissingProductFails() {
        when(repository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.delete(5L));
        verify(repository, never()).save(any());
    }

    private Product storedProduct() {
        Product product = new Product();
        product.setId(5L);
        product.setTitle("Старое");
        product.setDescription("Описание");
        product.setPrice(new BigDecimal("10.00"));
        product.setImages(List.of("https://img/1.png", "https://img/2.png", "https://img/3.png"));
        product.setStockQuantity(1L);
        product.setCreatedAt(LocalDateTime.now());
        return product;
    }
}
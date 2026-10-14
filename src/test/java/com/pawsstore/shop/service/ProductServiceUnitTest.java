package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.PagedResponse;
import com.pawsstore.shop.dto.ProductProjection;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

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
}
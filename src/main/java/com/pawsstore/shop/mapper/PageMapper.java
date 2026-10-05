package com.pawsstore.shop.mapper;

import com.pawsstore.shop.dto.PagedResponse;
import com.pawsstore.shop.dto.ProductProjection;
import org.springframework.data.domain.Page;


public final class PageMapper {

    private PageMapper() {
    }

    public static PagedResponse<ProductProjection> toResponse(Page<ProductProjection> page) {

        PagedResponse.MetaInformation metaInformation = new PagedResponse.MetaInformation();
        metaInformation.setCurrentPage(page.getPageable().getPageNumber() + 1);
        metaInformation.setTotalPages(page.getTotalPages());
        metaInformation.setLimit(page.getPageable().getPageSize());
        metaInformation.setTotalItems(page.getTotalElements());

        return new PagedResponse<>(page.getContent(), metaInformation);
    }
}

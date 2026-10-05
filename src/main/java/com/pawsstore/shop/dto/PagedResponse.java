package com.pawsstore.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public record PagedResponse<T>(
        List<T> elements,
        MetaInformation meta
) {
    @Getter
    @Setter
    public static class MetaInformation {
        long totalItems;
        int totalPages;
        int currentPage;
        int limit;
    }
}

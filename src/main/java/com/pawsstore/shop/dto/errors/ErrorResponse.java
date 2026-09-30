package com.pawsstore.shop.dto.errors;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
        String code,
        String message,
        Instant timestamp,
        Map<String, String> details
) {
    public ErrorResponse(String code, String message, Map<String, String> map) {
        this(code, message, Instant.now(), map);
    }
}

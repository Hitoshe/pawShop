package com.pawsstore.shop.exceptions;

public class CartAccessDeniedException extends RuntimeException {
    public CartAccessDeniedException(String message) {
        super(message);
    }
}

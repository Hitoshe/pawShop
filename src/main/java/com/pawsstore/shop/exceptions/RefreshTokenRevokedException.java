package com.pawsstore.shop.exceptions;

public class RefreshTokenRevokedException extends RuntimeException {
    public RefreshTokenRevokedException(String message) {
        super(message);
    }
}

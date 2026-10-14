package com.pawsstore.shop.exceptions;

import com.pawsstore.shop.dto.errors.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException exception, Locale locale) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        String message = messageSource.getMessage("validation.error", null, locale);

        return ResponseEntity.badRequest().body(
                new ErrorResponse("VALIDATION_ERROR", message, errors)
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidLogin(BadCredentialsException exception) {
        return ResponseEntity.status(401).body(new ErrorResponse("INVALID_CREDENTIALS",
                exception.getMessage(),
                new HashMap<>()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException exception) {
        return ResponseEntity.status(409).body(new ErrorResponse("EMAIL_EXISTS",
                exception.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleRefreshTokenExceptions(ExpiredRefreshTokenException exception) {
        return ResponseEntity.status(401).body(new ErrorResponse("TOKEN_EXPIRED",
                exception.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleRefreshTokenRevoked(RefreshTokenRevokedException exception) {
        return ResponseEntity.status(401).body(new ErrorResponse("TOKEN_REVOKED",
                exception.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleRefreshTokenInvalid(RefreshTokenInvalidException exception) {
        return ResponseEntity.status(401).body(new ErrorResponse("TOKEN_INVALID",
                exception.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException exception) {
        return ResponseEntity.status(404).body(new ErrorResponse("PRODUCT_NOT_FOUND",
                exception.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleCartItemNotFound(CartItemNotFoundException exception) {
        return ResponseEntity.status(404).body(new ErrorResponse("CART_ITEM_NOT_FOUND",
                exception.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity.status(404).body(new ErrorResponse("USER_NOT_FOUND",
                exception.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleCartAccessDenied(CartAccessDeniedException exception) {
        return ResponseEntity.status(403).body(new ErrorResponse("CART_FORBIDDEN",
                exception.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInsufficientStock(InsufficientStockException exception) {
        return ResponseEntity.status(409).body(new ErrorResponse("INSUFFICIENT_STOCK",
                exception.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidPromo(InvalidPromoCodeException exception) {
        return ResponseEntity.status(400).body(new ErrorResponse("PROMO_INVALID",
                exception.getMessage(), new HashMap<>()));
    }

}

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
        return ResponseEntity.badRequest().body(new ErrorResponse("INVALID_CREDENTIALS",
                "Неправильный логин или пароль",
                new HashMap<>()));
    }


}

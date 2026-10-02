package com.pawsstore.shop.controller;

import com.pawsstore.shop.dto.UserRequest;
import com.pawsstore.shop.dto.UserResponse;
import com.pawsstore.shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;


    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {
        String name = authentication.getName();
        return userService.findByEmail(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid UserRequest request, Locale locale) {
        return userService.createUser(request, locale);
    }
}

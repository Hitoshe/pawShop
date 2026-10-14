package com.pawsstore.shop.controller;

import com.pawsstore.shop.dto.cart.AddCartItemRequest;
import com.pawsstore.shop.dto.cart.ApplyPromoRequest;
import com.pawsstore.shop.dto.cart.CartResponse;
import com.pawsstore.shop.dto.cart.PromoApplicationResponse;
import com.pawsstore.shop.dto.cart.UpdateCartItemRequest;
import com.pawsstore.shop.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartResponse getCart(Authentication authentication) {
        return cartService.getCart(authentication.getName());
    }

    @PostMapping("/items")
    public CartResponse addItem(@RequestBody @Valid AddCartItemRequest request, Authentication authentication) {
        return cartService.addItem(authentication.getName(), request);
    }

    @PatchMapping("/items/{id}")
    public CartResponse updateItem(@PathVariable Long id,
                                   @RequestBody @Valid UpdateCartItemRequest request,
                                   Authentication authentication) {
        return cartService.updateItem(authentication.getName(), id, request);
    }

    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(@PathVariable Long id, Authentication authentication) {
        cartService.removeItem(authentication.getName(), id);
    }

    @PostMapping("/apply-promo")
    public PromoApplicationResponse applyPromo(@RequestBody @Valid ApplyPromoRequest request,
                                               Authentication authentication) {
        return cartService.applyPromo(authentication.getName(), request);
    }
}

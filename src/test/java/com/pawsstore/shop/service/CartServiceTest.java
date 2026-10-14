package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.cart.AddCartItemRequest;
import com.pawsstore.shop.dto.cart.ApplyPromoRequest;
import com.pawsstore.shop.dto.cart.CartResponse;
import com.pawsstore.shop.dto.cart.PromoApplicationResponse;
import com.pawsstore.shop.dto.cart.UpdateCartItemRequest;
import com.pawsstore.shop.exceptions.CartAccessDeniedException;
import com.pawsstore.shop.exceptions.InsufficientStockException;
import com.pawsstore.shop.exceptions.InvalidPromoCodeException;
import com.pawsstore.shop.model.Cart;
import com.pawsstore.shop.model.CartItem;
import com.pawsstore.shop.model.Product;
import com.pawsstore.shop.model.PromoCode;
import com.pawsstore.shop.model.User;
import com.pawsstore.shop.repository.CartItemRepository;
import com.pawsstore.shop.repository.CartRepository;
import com.pawsstore.shop.repository.ProductRepository;
import com.pawsstore.shop.repository.PromoCodeRepository;
import com.pawsstore.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private PromoCodeRepository promoCodeRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void addItemIncreasesQuantityWhenProductAlreadyInCart() {
        User user = user(1L, "a@a.com");
        Cart cart = cart(10L, user);
        Product product = product(5L, "100.00", 10L);
        CartItem existing = item(100L, cart, product, 2);

        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(5L)).thenReturn(Optional.of(product));
        when(cartItemRepository.findByCartIdAndProductId(10L, 5L)).thenReturn(Optional.of(existing));
        when(cartItemRepository.save(existing)).thenReturn(existing);
        when(cartItemRepository.findAllWithProductByCartId(10L)).thenReturn(List.of(existing));

        CartResponse response = cartService.addItem("a@a.com", new AddCartItemRequest(5L, 3));

        assertEquals(5, existing.getQuantity());
        assertEquals(new BigDecimal("500.00"), response.subtotal());
        verify(cartRepository, never()).save(any());
    }

    @Test
    void addItemRejectsQuantityAboveStock() {
        User user = user(1L, "a@a.com");
        Cart cart = cart(10L, user);
        Product product = product(5L, "100.00", 4L);

        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(5L)).thenReturn(Optional.of(product));
        when(cartItemRepository.findByCartIdAndProductId(10L, 5L)).thenReturn(Optional.empty());

        assertThrows(InsufficientStockException.class,
                () -> cartService.addItem("a@a.com", new AddCartItemRequest(5L, 5)));
        verify(cartItemRepository, never()).save(any());
    }

    @Test
    void updateItemOfAnotherUserIsForbidden() {
        User owner = user(1L, "a@a.com");
        User stranger = user(2L, "b@b.com");
        Cart cart = cart(10L, owner);
        CartItem existing = item(100L, cart, product(5L, "10.00", 5L), 1);

        when(userRepository.findByEmail("b@b.com")).thenReturn(Optional.of(stranger));
        when(cartItemRepository.findWithOwnerById(100L)).thenReturn(Optional.of(existing));

        assertThrows(CartAccessDeniedException.class,
                () -> cartService.updateItem("b@b.com", 100L, new UpdateCartItemRequest(2)));
    }

    @Test
    void applyPromoCalculatesTenPercentAndDoesNotSaveTheCode() {
        User user = user(1L, "a@a.com");
        Cart cart = cart(10L, user);
        CartItem line = item(100L, cart, product(5L, "200.00", 5L), 1);
        PromoCode promo = new PromoCode();
        promo.setCode("SAVE10");
        promo.setDiscountPercentage(new BigDecimal("10.00"));
        promo.setActive(true);

        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(user));
        when(promoCodeRepository.findByCodeIgnoreCase("save10")).thenReturn(Optional.of(promo));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findAllWithProductByCartId(10L)).thenReturn(List.of(line));

        PromoApplicationResponse response = cartService.applyPromo("a@a.com", new ApplyPromoRequest("save10"));

        assertEquals(new BigDecimal("200.00"), response.subtotal());
        assertEquals(new BigDecimal("10.00"), response.discountPercentage());
        assertEquals(new BigDecimal("20.00"), response.discountAmount());
        assertEquals(new BigDecimal("180.00"), response.total());
        verify(cartRepository, never()).save(any());
    }

    @Test
    void inactivePromoIsRejected() {
        User user = user(1L, "a@a.com");
        PromoCode promo = new PromoCode();
        promo.setActive(false);
        promo.setDiscountPercentage(new BigDecimal("10.00"));

        when(userRepository.findByEmail("a@a.com")).thenReturn(Optional.of(user));
        when(promoCodeRepository.findByCodeIgnoreCase("SAVE10")).thenReturn(Optional.of(promo));

        assertThrows(InvalidPromoCodeException.class,
                () -> cartService.applyPromo("a@a.com", new ApplyPromoRequest("SAVE10")));
    }

    private User user(Long id, String email) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        return user;
    }

    private Cart cart(Long id, User user) {
        Cart cart = new Cart();
        cart.setId(id);
        cart.setUser(user);
        return cart;
    }

    private Product product(Long id, String price, long stock) {
        Product product = new Product();
        product.setId(id);
        product.setTitle("Корм");
        product.setPrice(new BigDecimal(price));
        product.setStockQuantity(stock);
        return product;
    }

    private CartItem item(Long id, Cart cart, Product product, int quantity) {
        CartItem item = new CartItem();
        item.setId(id);
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        return item;
    }
}

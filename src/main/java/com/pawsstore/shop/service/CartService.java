package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.cart.AddCartItemRequest;
import com.pawsstore.shop.dto.cart.ApplyPromoRequest;
import com.pawsstore.shop.dto.cart.CartResponse;
import com.pawsstore.shop.dto.cart.PromoApplicationResponse;
import com.pawsstore.shop.dto.cart.UpdateCartItemRequest;
import com.pawsstore.shop.mapper.CartMapper;
import com.pawsstore.shop.exceptions.CartAccessDeniedException;
import com.pawsstore.shop.exceptions.CartItemNotFoundException;
import com.pawsstore.shop.exceptions.InsufficientStockException;
import com.pawsstore.shop.exceptions.InvalidPromoCodeException;
import com.pawsstore.shop.exceptions.ProductNotFoundException;
import com.pawsstore.shop.exceptions.UserNotFoundException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private static final int MONEY_SCALE = 2;

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final UserRepository userRepository;

    public CartResponse getCart(String email) {
        User user = currentUser(email);
        return toResponse(getOrCreateCart(user));
    }

    public CartResponse addItem(String email, AddCartItemRequest request) {
        User user = currentUser(email);
        Cart cart = getOrCreateCart(user);
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ProductNotFoundException("Товар не найден"));

        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);
        int quantity = request.quantity();
        if (item != null) {
            quantity = item.getQuantity() + request.quantity();
        }
        assertInStock(product, quantity);

        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
        }
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return toResponse(cart);
    }

    public CartResponse updateItem(String email, Long itemId, UpdateCartItemRequest request) {
        User user = currentUser(email);
        CartItem item = ownedItem(itemId, user);
        assertInStock(item.getProduct(), request.quantity());
        item.setQuantity(request.quantity());
        cartItemRepository.save(item);
        return toResponse(item.getCart());
    }

    public void removeItem(String email, Long itemId) {
        User user = currentUser(email);
        CartItem item = ownedItem(itemId, user);
        cartItemRepository.delete(item);
    }

    @Transactional(readOnly = true)
    public PromoApplicationResponse applyPromo(String email, ApplyPromoRequest request) {
        User user = currentUser(email);
        PromoCode promo = promoCodeRepository.findByCodeIgnoreCase(request.code().trim())
                .filter(PromoCode::isActive)
                .orElseThrow(() -> new InvalidPromoCodeException("Промокод не найден или не активен"));

        BigDecimal subtotal = cartRepository.findByUserId(user.getId())
                .map(cart -> toResponse(cart).subtotal())
                .orElse(CartMapper.money(BigDecimal.ZERO));
        BigDecimal discount = subtotal
                .multiply(promo.getDiscountPercentage())
                .divide(BigDecimal.valueOf(100), MONEY_SCALE, RoundingMode.HALF_UP);
        return new PromoApplicationResponse(
                subtotal,
                promo.getDiscountPercentage(),
                discount,
                subtotal.subtract(discount)
        );
    }

    private User currentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    private CartItem ownedItem(Long itemId, User user) {
        CartItem item = cartItemRepository.findWithOwnerById(itemId)
                .orElseThrow(() -> new CartItemNotFoundException("Позиция корзины не найдена"));
        if (!item.getCart().getUser().getId().equals(user.getId())) {
            throw new CartAccessDeniedException("Нельзя изменять чужую корзину");
        }
        return item;
    }

    private void assertInStock(Product product, int quantity) {
        long stock = product.getStockQuantity();
        if (quantity > stock) {
            throw new InsufficientStockException(
                    "На складе только " + stock + " шт., запрошено " + quantity
            );
        }
    }

    private CartResponse toResponse(Cart cart) {
        return CartMapper.toResponse(cart, cartItemRepository.findAllWithProductByCartId(cart.getId()));
    }
}

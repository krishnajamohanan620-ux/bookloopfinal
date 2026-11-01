package com.bookloop.service;

import com.bookloop.entity.Book;
import com.bookloop.entity.CartItem;
import com.bookloop.entity.User;
import com.bookloop.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CartService
 * Manages saving and removing selected academic books in a student's cart.
 */
@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getCartForUser(User user) {
        return cartItemRepository.findByUserOrderByAddedAtDesc(user);
    }

    public long getCartCountForUser(User user) {
        if (user == null) return 0;
        return cartItemRepository.countByUser(user);
    }

    public double calculateTotal(List<CartItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getBook().getPrice() != null ? item.getBook().getPrice() : 0.0)
                .sum();
    }

    @Transactional
    public boolean addBookToCart(User user, Book book) {
        if (cartItemRepository.existsByUserAndBook(user, book)) {
            return false; // Already in cart
        }
        CartItem item = new CartItem(user, book);
        cartItemRepository.save(item);
        return true;
    }

    @Transactional
    public boolean removeCartItem(Long cartItemId, User user) {
        Optional<CartItem> item = cartItemRepository.findById(cartItemId);
        if (item.isPresent() && item.get().getUser().getId().equals(user.getId())) {
            cartItemRepository.delete(item.get());
            return true;
        }
        return false;
    }

    @Transactional
    public void clearCart(User user) {
        List<CartItem> items = cartItemRepository.findByUserOrderByAddedAtDesc(user);
        cartItemRepository.deleteAll(items);
    }
}

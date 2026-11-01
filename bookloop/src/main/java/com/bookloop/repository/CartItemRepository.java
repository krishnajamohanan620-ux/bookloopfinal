package com.bookloop.repository;

import com.bookloop.entity.Book;
import com.bookloop.entity.CartItem;
import com.bookloop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * CartItemRepository
 * Handles database operations for student cart selections.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserOrderByAddedAtDesc(User user);

    Optional<CartItem> findByUserAndBook(User user, Book book);

    boolean existsByUserAndBook(User user, Book book);

    long countByUser(User user);

    void deleteByUserAndBook(User user, Book book);
}

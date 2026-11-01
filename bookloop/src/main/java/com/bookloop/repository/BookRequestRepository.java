package com.bookloop.repository;

import com.bookloop.entity.BookRequest;
import com.bookloop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BookRequestRepository
 * Manages queries for student book requests in MySQL.
 */
@Repository
public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
    List<BookRequest> findAllByOrderByCreatedAtDesc();
    List<BookRequest> findByRequesterOrderByCreatedAtDesc(User requester);
}

package com.bookloop.repository;

import com.bookloop.entity.Book;
import com.bookloop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BookRepository
 * Provides database queries for searching and filtering books by title, course, and semester.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByOrderByCreatedAtDesc();

    List<Book> findBySellerOrderByCreatedAtDesc(User seller);

    @Query("SELECT b FROM Book b WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:course IS NULL OR :course = '' OR b.course = :course) AND " +
           "(:semester IS NULL OR :semester = '' OR b.semester = :semester) " +
           "ORDER BY b.createdAt DESC")
    List<Book> searchBooks(@Param("keyword") String keyword,
                           @Param("course") String course,
                           @Param("semester") String semester);
}

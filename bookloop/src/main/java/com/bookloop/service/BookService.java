package com.bookloop.service;

import com.bookloop.dto.BookDto;
import com.bookloop.entity.Book;
import com.bookloop.entity.User;
import com.bookloop.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * BookService
 * Handles listing new books for sale/exchange, searching by title, and filtering by course/semester.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Book> searchBooks(String keyword, String course, String semester) {
        String trimmedKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
        String trimmedCourse = (course != null && !course.trim().isEmpty()) ? course.trim() : null;
        String trimmedSemester = (semester != null && !semester.trim().isEmpty()) ? semester.trim() : null;

        if (trimmedKeyword == null && trimmedCourse == null && trimmedSemester == null) {
            return bookRepository.findAllByOrderByCreatedAtDesc();
        }

        return bookRepository.searchBooks(trimmedKeyword, trimmedCourse, trimmedSemester);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Book createListing(BookDto dto, User seller) {
        Book book = new Book(
                dto.getTitle().trim(),
                dto.getAuthor().trim(),
                dto.getCourse().trim(),
                dto.getSemester().trim(),
                dto.getCollege().trim(),
                dto.getPrice(),
                dto.getBookCondition().trim(),
                dto.getListingType().trim(),
                dto.getSellerContact().trim(),
                dto.getDescription() != null ? dto.getDescription().trim() : "",
                seller
        );
        return bookRepository.save(book);
    }

    public List<Book> getBooksBySeller(User seller) {
        return bookRepository.findBySellerOrderByCreatedAtDesc(seller);
    }

    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}

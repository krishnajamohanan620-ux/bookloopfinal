package com.bookloop;

import com.bookloop.dto.BookDto;
import com.bookloop.dto.RegisterDto;
import com.bookloop.dto.RequestDto;
import com.bookloop.entity.Book;
import com.bookloop.entity.BookRequest;
import com.bookloop.entity.CartItem;
import com.bookloop.entity.User;
import com.bookloop.repository.BookRepository;
import com.bookloop.repository.BookRequestRepository;
import com.bookloop.repository.CartItemRepository;
import com.bookloop.repository.UserRepository;
import com.bookloop.service.BookRequestService;
import com.bookloop.service.BookService;
import com.bookloop.service.CartService;
import com.bookloop.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookLoopApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookRequestService requestService;

    @Autowired
    private BookRequestRepository requestRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Context Loads & MySQL Connection Verified")
    void contextLoads() {
        assertNotNull(userRepository);
        assertNotNull(bookRepository);
    }

    @Test
    @DisplayName("Requirement 1: User Registration with Hashed Password")
    void testUserRegistrationAndBCryptHashing() {
        String testEmail = "test.student." + System.currentTimeMillis() + "@campus.edu";
        RegisterDto dto = new RegisterDto();
        dto.setName("Test Student");
        dto.setEmail(testEmail);
        dto.setCollegeName("Apex Institute of Technology");
        dto.setPassword("SecretPassword123");
        dto.setConfirmPassword("SecretPassword123");

        assertTrue(dto.isPasswordMatching());

        User registeredUser = userService.registerUser(dto);
        assertNotNull(registeredUser.getId());
        assertEquals("Test Student", registeredUser.getName());
        assertEquals(testEmail.toLowerCase(), registeredUser.getEmail());

        // Password MUST be hashed with BCrypt, never plain text
        assertNotEquals("SecretPassword123", registeredUser.getPassword());
        assertTrue(passwordEncoder.matches("SecretPassword123", registeredUser.getPassword()));
    }

    @Test
    @DisplayName("Requirement 3 & 4: Sell Book and Search/Filter by Title, Course, Semester")
    void testBookCreationAndSearch() {
        User seller = userRepository.findAll().stream().findFirst().orElseGet(() -> {
            RegisterDto u = new RegisterDto();
            u.setName("Seller One");
            u.setEmail("seller@campus.edu");
            u.setCollegeName("Apex Tech");
            u.setPassword("pass123");
            u.setConfirmPassword("pass123");
            return userService.registerUser(u);
        });

        BookDto dto = new BookDto();
        dto.setTitle("Discrete Mathematics and Its Applications");
        dto.setAuthor("Kenneth Rosen");
        dto.setCourse("Computer Science & Engineering");
        dto.setSemester("Semester 3");
        dto.setCollege("Apex Tech");
        dto.setPrice(320.0);
        dto.setBookCondition("Good Condition");
        dto.setListingType("Sale");
        dto.setSellerContact("+91 98765 11111");
        dto.setDescription("Clean textbook for discrete maths");

        Book created = bookService.createListing(dto, seller);
        assertNotNull(created.getId());

        // Search by keyword
        List<Book> searchTitle = bookService.searchBooks("Discrete", null, null);
        assertFalse(searchTitle.isEmpty());
        assertTrue(searchTitle.stream().anyMatch(b -> b.getTitle().contains("Discrete")));

        // Search by course filter
        List<Book> searchCourse = bookService.searchBooks(null, "Computer Science & Engineering", null);
        assertFalse(searchCourse.isEmpty());

        // Search by semester filter
        List<Book> searchSemester = bookService.searchBooks(null, null, "Semester 3");
        assertFalse(searchSemester.isEmpty());
    }

    @Test
    @DisplayName("Requirement 5: Need a Book Request Creation")
    void testBookRequest() {
        User requester = userRepository.findAll().stream().findFirst().orElse(null);

        RequestDto dto = new RequestDto();
        dto.setTitle("Compiler Design Principles");
        dto.setAuthor("Aho & Ullman (Dragon Book)");
        dto.setCourse("Computer Science & Engineering");
        dto.setSemester("Semester 6");
        dto.setContactInfo("requester@campus.edu");
        dto.setNotes("Need for end-semester project and labs");

        BookRequest request = requestService.createRequest(dto, requester);
        assertNotNull(request.getId());

        List<BookRequest> allRequests = requestService.getAllRequests();
        assertTrue(allRequests.stream().anyMatch(r -> r.getTitle().contains("Compiler Design")));
    }

    @Test
    @DisplayName("Requirement 6: Cart Add, Count, Total Calculation, and Remove")
    void testCartOperations() {
        User user = userRepository.findAll().stream().findFirst().orElseGet(() -> {
            RegisterDto u = new RegisterDto();
            u.setName("Cart User");
            u.setEmail("cartuser@campus.edu");
            u.setCollegeName("Apex Tech");
            u.setPassword("pass123");
            u.setConfirmPassword("pass123");
            return userService.registerUser(u);
        });

        List<Book> books = bookRepository.findAll();
        assertFalse(books.isEmpty(), "Demo books should be seeded");
        Book book = books.get(0);

        // Add to cart
        boolean added = cartService.addBookToCart(user, book);
        assertTrue(added);

        // Avoid duplicate add
        boolean addedAgain = cartService.addBookToCart(user, book);
        assertFalse(addedAgain);

        List<CartItem> items = cartService.getCartForUser(user);
        assertFalse(items.isEmpty());

        double total = cartService.calculateTotal(items);
        assertTrue(total >= 0.0);

        // Remove from cart
        Long cartItemId = items.get(0).getId();
        boolean removed = cartService.removeCartItem(cartItemId, user);
        assertTrue(removed);
    }
}

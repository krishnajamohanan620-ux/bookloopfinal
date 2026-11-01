package com.bookloop.config;

import com.bookloop.entity.Book;
import com.bookloop.entity.BookRequest;
import com.bookloop.entity.User;
import com.bookloop.repository.BookRepository;
import com.bookloop.repository.BookRequestRepository;
import com.bookloop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * DataInitializer
 * Seeds initial academic book exchange data (sample student user, textbooks, and requests)
 * so the application has demo data ready immediately upon first launch.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookRequestRepository requestRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           BookRepository bookRepository,
                           BookRequestRepository requestRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.requestRepository = requestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Only seed if no users exist
        if (userRepository.count() == 0) {
            // 1. Create a demo campus student account
            User demoUser = new User(
                    "Alex Johnson",
                    "student@campus.edu",
                    passwordEncoder.encode("student123"),
                    "Apex Institute of Technology"
            );
            userRepository.save(demoUser);

            User secondUser = new User(
                    "Priya Sharma",
                    "priya@campus.edu",
                    passwordEncoder.encode("priya123"),
                    "Apex Institute of Technology"
            );
            userRepository.save(secondUser);

            // 2. Add realistic campus academic books
            Book book1 = new Book(
                    "Introduction to Algorithms (CLRS 4th Ed)",
                    "Thomas H. Cormen, Charles E. Leiserson",
                    "Computer Science & Engineering",
                    "Semester 4",
                    "Apex Institute of Technology",
                    450.0,
                    "Like New",
                    "Sale",
                    "alex.j@campus.edu / +91 98765 43210",
                    "Essential textbook for Design and Analysis of Algorithms. No markings, pristine condition.",
                    demoUser
            );

            Book book2 = new Book(
                    "Operating System Concepts (Silberschatz)",
                    "Abraham Silberschatz, Peter B. Galvin",
                    "Computer Science & Engineering",
                    "Semester 5",
                    "Apex Institute of Technology",
                    380.0,
                    "Good Condition",
                    "Sale",
                    "priya@campus.edu / +91 91234 56789",
                    "Covers processes, memory management, and file systems. Few pencil highlights.",
                    secondUser
            );

            Book book3 = new Book(
                    "Database System Concepts (7th Edition)",
                    "Abraham Silberschatz, Henry F. Korth",
                    "Information Technology",
                    "Semester 4",
                    "Apex Institute of Technology",
                    120.0,
                    "Like New",
                    "Rental",
                    "alex.j@campus.edu",
                    "Available for full semester rental! Clean pages, includes SQL companion sheets.",
                    demoUser
            );

            Book book4 = new Book(
                    "Engineering Mechanics: Statics & Dynamics",
                    "R.C. Hibbeler",
                    "Mechanical Engineering",
                    "Semester 2",
                    "Apex Institute of Technology",
                    0.0,
                    "Fair / Highlighting",
                    "Exchange",
                    "priya@campus.edu",
                    "Looking to exchange this for Digital Logic Design textbook for next semester.",
                    secondUser
            );

            Book book5 = new Book(
                    "Microelectronic Circuits (8th Edition)",
                    "Adel S. Sedra, Kenneth C. Smith",
                    "Electronics & Communication",
                    "Semester 3",
                    "Apex Institute of Technology",
                    520.0,
                    "Brand New",
                    "Sale",
                    "+91 98765 00000",
                    "Unused copy bought by mistake. Completely sealed with access codes.",
                    demoUser
            );

            Book book6 = new Book(
                    "Artificial Intelligence: A Modern Approach",
                    "Stuart Russell, Peter Norvig",
                    "Artificial Intelligence & Data Science",
                    "Semester 6",
                    "Apex Institute of Technology",
                    490.0,
                    "Like New",
                    "Sale",
                    "priya@campus.edu",
                    "Standard reference for machine learning, search algorithms, and NLP fundamentals.",
                    secondUser
            );

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);
            bookRepository.save(book4);
            bookRepository.save(book5);
            bookRepository.save(book6);

            // 3. Add a sample student book request
            BookRequest req1 = new BookRequest(
                    "Computer Networks (Tanenbaum 6th Edition)",
                    "Andrew S. Tanenbaum",
                    "Computer Science & Engineering",
                    "Semester 5",
                    "Urgent: Needed for midterm prep next week. Happy to buy or rent!",
                    "student@campus.edu",
                    demoUser
            );

            BookRequest req2 = new BookRequest(
                    "Higher Engineering Mathematics",
                    "B.S. Grewal",
                    "Applied Sciences & Mathematics",
                    "Semester 1",
                    "First-year student looking for second-hand copy.",
                    "priya@campus.edu",
                    secondUser
            );

            requestRepository.save(req1);
            requestRepository.save(req2);
        }
    }
}

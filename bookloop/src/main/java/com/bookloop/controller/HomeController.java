package com.bookloop.controller;

import com.bookloop.entity.Book;
import com.bookloop.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * HomeController
 * Displays the main campus book exchange feed with search by title and course/semester filters.
 */
@Controller
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/home")
    public String home(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "course", required = false) String course,
                       @RequestParam(value = "semester", required = false) String semester,
                       @RequestParam(value = "welcome", required = false) String welcome,
                       @RequestParam(value = "listed", required = false) String listed,
                       @RequestParam(value = "addedToCart", required = false) String addedToCart,
                       @RequestParam(value = "alreadyInCart", required = false) String alreadyInCart,
                       Model model) {

        List<Book> books = bookService.searchBooks(keyword, course, semester);

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword != null ? keyword : "");
        model.addAttribute("selectedCourse", course != null ? course : "");
        model.addAttribute("selectedSemester", semester != null ? semester : "");

        // Predefined Academic Courses and Semesters for filtering
        model.addAttribute("courses", Arrays.asList(
                "Computer Science & Engineering",
                "Information Technology",
                "Electronics & Communication",
                "Electrical Engineering",
                "Mechanical Engineering",
                "Civil Engineering",
                "Artificial Intelligence & Data Science",
                "Business Administration",
                "Applied Sciences & Mathematics"
        ));

        model.addAttribute("semesters", Arrays.asList(
                "Semester 1", "Semester 2", "Semester 3", "Semester 4",
                "Semester 5", "Semester 6", "Semester 7", "Semester 8"
        ));

        if (welcome != null) {
            model.addAttribute("successMessage", "Welcome to BookLoop! Start exploring or listing textbooks.");
        }
        if (listed != null) {
            model.addAttribute("successMessage", "Your book listing has been published successfully!");
        }
        if (addedToCart != null) {
            model.addAttribute("successMessage", "Book added to your cart!");
        }
        if (alreadyInCart != null) {
            model.addAttribute("infoMessage", "This book is already in your cart.");
        }

        return "home";
    }
}

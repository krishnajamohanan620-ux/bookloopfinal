package com.bookloop.controller;

import com.bookloop.dto.BookDto;
import com.bookloop.entity.User;
import com.bookloop.service.BookService;
import com.bookloop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.Optional;

/**
 * BookController
 * Handles the "Sell a Book" listing form and saves book data in MySQL.
 */
@Controller
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    private void populateFormOptions(Model model) {
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

        model.addAttribute("conditions", Arrays.asList(
                "Brand New", "Like New", "Good Condition", "Fair / Highlighting"
        ));

        model.addAttribute("listingTypes", Arrays.asList(
                "Sale", "Rental", "Exchange"
        ));
    }

    @GetMapping("/sell")
    public String showSellForm(Model model, Authentication authentication) {
        BookDto bookDto = new BookDto();

        // Pre-fill college and contact info from user if available
        if (authentication != null) {
            Optional<User> user = userService.findByEmail(authentication.getName());
            user.ifPresent(u -> {
                bookDto.setCollege(u.getCollegeName());
                bookDto.setSellerContact(u.getEmail());
            });
        }

        model.addAttribute("bookDto", bookDto);
        populateFormOptions(model);
        return "sell";
    }

    @PostMapping("/sell")
    public String submitListing(@Valid @ModelAttribute("bookDto") BookDto bookDto,
                                BindingResult bindingResult,
                                Authentication authentication,
                                Model model) {
        if (bindingResult.hasErrors()) {
            populateFormOptions(model);
            return "sell";
        }

        User seller = null;
        if (authentication != null) {
            seller = userService.findByEmail(authentication.getName()).orElse(null);
        }

        bookService.createListing(bookDto, seller);

        return "redirect:/home?listed=true";
    }
}

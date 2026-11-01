package com.bookloop.controller;

import com.bookloop.dto.RequestDto;
import com.bookloop.entity.BookRequest;
import com.bookloop.entity.User;
import com.bookloop.service.BookRequestService;
import com.bookloop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * RequestController
 * Handles the "Need a Book" feature: student book requests board and submission form.
 */
@Controller
public class RequestController {

    private final BookRequestService requestService;
    private final UserService userService;

    public RequestController(BookRequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    private void populateDropdowns(Model model) {
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
    }

    @GetMapping("/need")
    public String showNeedPage(@RequestParam(value = "requested", required = false) String requested,
                               Model model,
                               Authentication authentication) {
        RequestDto requestDto = new RequestDto();

        if (authentication != null) {
            Optional<User> user = userService.findByEmail(authentication.getName());
            user.ifPresent(u -> requestDto.setContactInfo(u.getEmail()));
        }

        List<BookRequest> requests = requestService.getAllRequests();

        model.addAttribute("requestDto", requestDto);
        model.addAttribute("requests", requests);
        populateDropdowns(model);

        if (requested != null) {
            model.addAttribute("successMessage", "Your book request has been posted to the campus board!");
        }

        return "need";
    }

    @PostMapping("/need")
    public String submitRequest(@Valid @ModelAttribute("requestDto") RequestDto requestDto,
                                BindingResult bindingResult,
                                Authentication authentication,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("requests", requestService.getAllRequests());
            populateDropdowns(model);
            return "need";
        }

        User requester = null;
        if (authentication != null) {
            requester = userService.findByEmail(authentication.getName()).orElse(null);
        }

        requestService.createRequest(requestDto, requester);

        return "redirect:/need?requested=true";
    }
}

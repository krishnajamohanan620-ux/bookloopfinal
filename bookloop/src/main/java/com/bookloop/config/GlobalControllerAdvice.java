package com.bookloop.config;

import com.bookloop.entity.User;
import com.bookloop.service.CartService;
import com.bookloop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

/**
 * GlobalControllerAdvice
 * Automatically injects the logged-in user and cart count into all Thymeleaf model templates,
 * so the navigation bar always displays live user information.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserService userService;
    private final CartService cartService;

    public GlobalControllerAdvice(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            Optional<User> user = userService.findByEmail(auth.getName());
            return user.orElse(null);
        }
        return null;
    }

    @ModelAttribute("cartItemCount")
    public long getCartItemCount() {
        User user = getCurrentUser();
        if (user != null) {
            return cartService.getCartCountForUser(user);
        }
        return 0;
    }
}

package com.bookloop.controller;

import com.bookloop.entity.Book;
import com.bookloop.entity.CartItem;
import com.bookloop.entity.User;
import com.bookloop.service.BookService;
import com.bookloop.service.CartService;
import com.bookloop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

/**
 * CartController
 * Manages the student's cart / selected books page.
 * Enables adding books from the homepage, removing items, and viewing total cost.
 */
@Controller
public class CartController {

    private final CartService cartService;
    private final BookService bookService;
    private final UserService userService;

    public CartController(CartService cartService, BookService bookService, UserService userService) {
        this.cartService = cartService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String viewCart(@RequestParam(value = "removed", required = false) String removed,
                           @RequestParam(value = "cleared", required = false) String cleared,
                           Authentication authentication,
                           Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        List<CartItem> items = cartService.getCartForUser(user);
        double total = cartService.calculateTotal(items);

        model.addAttribute("cartItems", items);
        model.addAttribute("totalPrice", total);

        if (removed != null) {
            model.addAttribute("infoMessage", "Item removed from your cart.");
        }
        if (cleared != null) {
            model.addAttribute("infoMessage", "Cart has been cleared.");
        }

        return "cart";
    }

    @PostMapping("/cart/add/{bookId}")
    public String addToCart(@PathVariable("bookId") Long bookId,
                            Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName()).orElse(null);
        Optional<Book> bookOpt = bookService.findById(bookId);

        if (user != null && bookOpt.isPresent()) {
            boolean added = cartService.addBookToCart(user, bookOpt.get());
            if (added) {
                return "redirect:/home?addedToCart=true";
            } else {
                return "redirect:/home?alreadyInCart=true";
            }
        }

        return "redirect:/home";
    }

    @PostMapping("/cart/remove/{cartItemId}")
    public String removeFromCart(@PathVariable("cartItemId") Long cartItemId,
                                 Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName()).orElse(null);
        if (user != null) {
            cartService.removeCartItem(cartItemId, user);
        }

        return "redirect:/cart?removed=true";
    }

    @PostMapping("/cart/clear")
    public String clearCart(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName()).orElse(null);
        if (user != null) {
            cartService.clearCart(user);
        }

        return "redirect:/cart?cleared=true";
    }
}

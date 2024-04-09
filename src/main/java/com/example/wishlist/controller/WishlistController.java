package com.example.wishlist.controller;

import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController() {
        this.wishlistService = new WishlistService();
    }

    @GetMapping(path="")
    public String index(Model model) {
        model.addAttribute("wishlist", new Wishlist());
        return "index";
    }

    @GetMapping("/create")
    public String createWishlist(Model model) {
        model.addAttribute("createWishlist", new Wishlist());
        return "createWishlist";
    }

}

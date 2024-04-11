package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController() {
        this.wishlistService = new WishlistService();
    }

    @GetMapping(path = "")
    public String index(Model model) throws SQLException {
        model.addAttribute("wishlist", wishlistService.getAllFromRepository());
        return "index";
    }

    @GetMapping("/create")
    public String createWishlist(Model model) {
        Wishlist newWishlist = new Wishlist();
        model.addAttribute("createWishlist", newWishlist);
        return "createWishlist";
    }

    @PostMapping(path="/create")
    public String createNewWishList(@ModelAttribute Wishlist wishlist) throws SQLException{
        wishlistService.createWishlistFromRepository(wishlist);
        return "redirect:/wishlist";
    }

    @PostMapping("/newwishlist")
    public String newWishlist(@ModelAttribute("wishlist") Wishlist wishlist) {
        return "newWishlist";
    }

    @GetMapping("/createwish")
    public String createWish(Model model) {
        model.addAttribute("createWish", new Wish());
        return "createWish";
    }

    @DeleteMapping("/delete")
    public String deleteWish(@RequestParam("wishId") Long wishId) {
        //wishlistService.deleteWish(wishId);
        return "redirect:/wishlist/newwishlist";
    }
}

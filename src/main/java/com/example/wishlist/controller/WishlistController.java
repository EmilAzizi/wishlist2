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

    @GetMapping("/{ID}/update")
    public String showWishlistToUpdate(@PathVariable int ID, Model model){
        Wishlist wishlist = wishlistService.findByIDFromRepository(ID);
        model.addAttribute("wishlistToUpdate", wishlist);
        return "update";
    }

    @PostMapping("/update")
    public String updateWishlist(Wishlist wishlistToUpdate) throws SQLException {
        wishlistService.updateWishlist(wishlistToUpdate, wishlistToUpdate.getName());
        return "redirect:/wishlist";
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

    @PostMapping("/{ID}/delete")
    public String deleteWishlist(@PathVariable int ID) throws SQLException {
        wishlistService.deleteWishlist(ID);
        return "redirect:/wishlist";
    }

    @GetMapping("/{ID}/wishes")
    public String viewWishlist(@PathVariable int ID, Model model){
        Wishlist wishlist = wishlistService.findByIDFromRepository(ID);
        model.addAttribute("wishes", wishlist);
        model.addAttribute("wishlistID", ID);
        return "viewWishes";
    }

    @GetMapping("/{ID}/wishes/createWish")
    public String createWish(@PathVariable int ID, Model model){
        Wish wish = new Wish();
        Wishlist wishlist = wishlistService.findByIDFromRepository(ID);
        model.addAttribute("wishlistID", wishlist.getID());
        model.addAttribute("wish", wish);
        return "createWish";
    }

    @PostMapping("/{ID}/wishes/createWish")
    public String createWish(@PathVariable int ID, @ModelAttribute Wish WishFromUser) throws SQLException {
        wishlistService.createWish(ID, WishFromUser);
        return "redirect:/wishlist/{ID}/wishes";
    }

    @GetMapping("/{wishlistID}/wishes/{wishID}/updateWish")
    public String updateWish(@PathVariable int wishID, Model model, @PathVariable int wishlistID){
        Wish wish = wishlistService.findWishByIDFromRepository(wishlistID, wishID);
        model.addAttribute("wishlistID", wishlistID);
        model.addAttribute("wishID", wishID);
        model.addAttribute("wish", wish);
        return "updateWish";
    }

    @PostMapping("/{wishlistID}/wishes/{wishID}/updateWish")
    public String updateWish(@ModelAttribute Wish wishToBeUpdated, @PathVariable int wishID, @PathVariable int wishlistID) throws SQLException {
        wishlistService.updateWishFromRepository(wishlistID, wishID, wishToBeUpdated);
        return "redirect:/wishlist/" + wishlistID + "/wishes";
    }

    @PostMapping("/{wishlistID}/wishes/{wishID}/delete")
    public String deleteWish(@PathVariable int wishlistID, @PathVariable int wishID) throws SQLException {
        wishlistService.deleteWishFromRepository(wishlistID, wishID);
        return "redirect:/wishlist/" + wishlistID + "/wishes";
    }
}

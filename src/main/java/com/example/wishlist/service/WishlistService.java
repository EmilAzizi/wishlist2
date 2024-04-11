package com.example.wishlist.service;

import com.example.wishlist.model.Wishlist;
import com.example.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService() {
        this.wishlistRepository = new WishlistRepository();
    }

    public List<Wishlist> getAllFromRepository() throws SQLException {
        return wishlistRepository.getAll();
    }

    public void createWishlistFromRepository(Wishlist wishlist) throws SQLException{
        wishlistRepository.createWishList(wishlist);
    }

}

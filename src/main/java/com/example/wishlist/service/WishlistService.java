package com.example.wishlist.service;

import com.example.wishlist.model.Wishlist;
import com.example.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService() {
        this.wishlistRepository = new WishlistRepository();
    }

}

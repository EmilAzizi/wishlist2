package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

@Repository
public class WishlistRepository {
    List<Wishlist> wishlists = new ArrayList<>();
    WishlistDatabase database;

    public WishlistRepository(){
        database = new WishlistDatabase();
    }

    public void createWishList(Wishlist wishlist) throws SQLException {
        if(!wishlists.contains(database.createWishList(wishlist))){
            wishlists.add(database.createWishList(wishlist));
        }
    }

    public List<Wishlist> getAll() throws SQLException{
        for(Wishlist wishlist : wishlists){
            System.out.println(wishlist);
        }
        return wishlists;
    }


}

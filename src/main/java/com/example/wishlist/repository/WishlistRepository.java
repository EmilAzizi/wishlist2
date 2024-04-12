package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

@Repository
public class WishlistRepository {
    ArrayList<Wishlist> wishlists = new ArrayList<>();
    WishlistDatabase database;

    public WishlistRepository(){
        database = new WishlistDatabase();
    }

    public void createWishList(Wishlist wishlist) throws SQLException {
        int id = 0;
        if(!wishlists.contains(database.createWishList(wishlist))){
            wishlists.add(database.createWishList(wishlist));
        }

        for(Wishlist WL : wishlists){
            WL.setID(id);
            id++;
        }
    }

    public Wishlist findByID(int ID){
        Wishlist wishlistToFind = null;
        for(Wishlist wishlist : wishlists){
            if(wishlist.getID() == ID){
                wishlistToFind = wishlist;
            }
        }

        if(wishlistToFind != null){
            return wishlistToFind;
        } else {
            return null;
        }
    }

    public List<Wishlist> getAll(){
        return wishlists;
    }

    public void updateWishlist(Wishlist wishlistToUpdate) throws SQLException{
        for(Wishlist WL : wishlists){
            if(wishlistToUpdate.getID() == WL.getID()){
                wishlists.set(WL.getID(), wishlistToUpdate);
                database.checkForChangedNames(wishlists);
            }
        }
    }
}

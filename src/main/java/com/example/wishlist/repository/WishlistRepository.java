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

    public void updateWishlist(Wishlist wishlistToUpdate, String newName) throws SQLException{
        for(Wishlist WL : wishlists){
            if(wishlistToUpdate.getID() == WL.getID()){
                database.checkForChangedNames(wishlistToUpdate, newName);
                WL.setName(newName);
            }
        }
    }

    public void deleteWishlist(int ID) throws SQLException {
        Wishlist wishlistToBeRemoved = null;
        if(!wishlists.isEmpty()){
            for(Wishlist wishlist : wishlists){
                if(wishlist.getID() == ID){
                    wishlistToBeRemoved = wishlist;
                }
            }
        } else {
        }
        if(!wishlistToBeRemoved.equals(null)){
            wishlists.remove(wishlistToBeRemoved);
            database.removeWishlistFromDB(wishlistToBeRemoved);
        }
    }

    public void addWishToWishlist(int ID, Wish wishFromUser) throws SQLException {
        for(Wishlist wishlist : wishlists){
            if(wishlist.getID() == ID){
                Wish wishToBeAdded = database.createWish(wishlist, wishFromUser);
                wishlist.getWishList().add(wishToBeAdded);
            }
        }
    }
}

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
        Wishlist wishlistToCreate = database.createWishList(wishlist);

        if(!wishlists.contains(wishlistToCreate)){
            wishlists.add(wishlistToCreate);
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

    public Wish findWishByID(int wishlistID, int wishID){
        Wish wishToBeUpdated = null;
        Wishlist wishlistToJumpInto = null;
        for(Wishlist wishlist : wishlists){
            if(wishlist.getID() == wishlistID){
                wishlistToJumpInto = wishlist;
            }
        }
        for(Wish wish : wishlistToJumpInto.getWishList()){
            if(wish.getID() == wishID){
                wishToBeUpdated = wish;
            }
        }
        if(!wishToBeUpdated.equals(null)){
            return wishToBeUpdated;
        } else {
            return null;
        }
    }

    public void updateWish(int wishlistID, int wishID, Wish wishToUpdate) throws SQLException {
        Wishlist wishlistToSearch = null;
        for(Wishlist wishlist : wishlists){
            if(wishlist.getID() == wishlistID){
                wishlistToSearch = wishlist;
            }
        }
        for(Wish wish : wishlistToSearch.getWishList()){
            if(wish.getID() == wishID){
                wish.setName(wishToUpdate.getName());
                wish.setDescription(wishToUpdate.getDescription());
                wish.setAmount(wishToUpdate.getAmount());
                wish.setPrice(wishToUpdate.getPrice());
                database.updateWish(wish, wishlistToSearch);
            }
        }
    }
    public void deleteWish(int wishlistID, int wishID) throws SQLException {
        Wish wishToBeRemoved = null;
        Wishlist wishlistToSearch = null;
        for(Wishlist wishlist : wishlists){
            if(wishlist.getID() == wishlistID){
                wishlistToSearch = wishlist;
            }
        }
        for(Wish wishToFind : wishlistToSearch.getWishList()){
            if(wishToFind.getID() == wishID){
                wishToBeRemoved = wishToFind;
            }
        }
        if(!wishToBeRemoved.equals(null)){
            wishlistToSearch.getWishList().remove(wishToBeRemoved);
            database.deleteWish(wishToBeRemoved, wishlistToSearch);
        }
    }
}

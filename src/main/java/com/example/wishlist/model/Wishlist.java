package com.example.wishlist.model;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    public String name;
    private int ID = 0;
    public List<Wish> wishList;

    public Wishlist() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Wish> getWishList(){
        return wishList;
    }

    public String getName() {
        return name;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public int getID(){
        return ID;
    }

    public void setWishList(List<Wish> wishList) {
        this.wishList = wishList;
    }
}

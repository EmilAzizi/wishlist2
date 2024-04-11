package com.example.wishlist.model;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    private String name;
    private final int id = 0;
    public List<Wish> wishList;

    public Wishlist() {
        this.name = name;
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

    public void setWishList(List<Wish> wishList) {
        this.wishList = wishList;
    }
}

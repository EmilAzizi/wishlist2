package com.example.wishlist.model;

public class Wishlist {
    public String name;
    public int id;

    public Wishlist(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Wishlist(){

    }

    public String getName() {
        return name;
    }
}

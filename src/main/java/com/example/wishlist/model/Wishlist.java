package com.example.wishlist.model;

public class Wishlist {
    public String name;
    public String description;
    public int id;

    public Wishlist(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Wishlist(){

    }

    public String getName() {
        return name;
    }
}

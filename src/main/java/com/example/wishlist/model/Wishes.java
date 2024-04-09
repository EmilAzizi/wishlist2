package com.example.wishlist.model;

public class Wishes {
    public String name;
    public int price;
    public int quantity;
    public String description;

    public Wishes(String name, int price, int quantity, String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }
}

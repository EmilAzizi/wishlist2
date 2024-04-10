package com.example.wishlist.model;

public class Wish {
    public String name;
    public int price;
    public int quantity;
    public String description;

    public Wish(String name, int price, int quantity, String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public Wish() {
        }
}

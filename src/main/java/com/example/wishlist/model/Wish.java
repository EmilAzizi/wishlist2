package com.example.wishlist.model;

public class Wish {
    public String name;
    public int price;
    public int amount;
    public String description;
    public int ID = 0;

    public Wish() {}

    public Wish(int ID, String name, int price, int quantity, String description){
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.amount = quantity;
        this.description = description;
    }

        public int getID () {
            return ID;
        }

        public String getName () {
            return name;
        }

        public String getDescription () {
            return description;
        }

        public int getPrice () {
            return price;
        }

        public int getAmount() {
            return amount;
        }

        public void setName (String name){
            this.name = name;
        }

        public void setDescription (String description){
            this.description = description;
        }

        public void setPrice ( int price){
            this.price = price;
        }

        public void setAmount(int amount){
            this.amount = amount;
        }

        public void setID ( int ID){
            this.ID = ID;
        }
    }

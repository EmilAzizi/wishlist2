package com.example.wishlist.model;

public class Wish {
    public String name;
    public int price;
    public int quantity;
    public String description;
    private int ID = 0;

    public Wish() {
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

        public int getQuantity () {
            return quantity;
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

        public void setQuantity ( int quantity){
            this.quantity = quantity;
        }

        public void setID ( int ID){
            this.ID = ID;
        }

        @Override
        public String toString () {
            return "Wishes{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", quantity=" + quantity +
                    ", description='" + description + '\'' +
                    ", ID=" + ID +
                    '}';
        }

    }

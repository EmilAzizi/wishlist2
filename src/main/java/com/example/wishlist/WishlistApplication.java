package com.example.wishlist;

import com.example.wishlist.model.Wish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WishlistApplication {

    public static void main(String[] args) throws SQLException {
        Wish wish = new Wish(2, "PC", 5000, 1, "gamerPC");
        //int ID, String name, int price, int quantity, String description


        //database.insertWish(wish);
        //database.deleteWish(wish);
        List<Wish> wishList = new ArrayList<>();
        //database.createWishList();

//        for(Wish wishes : database.recieveWish()){
//            System.out.println(wishes.toString());
//        }
        SpringApplication.run(WishlistApplication.class, args);
    }

}

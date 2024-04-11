package com.example.wishlist.repository;

import com.example.wishlist.model.Wishes;
import com.example.wishlist.model.Wishlist;

import java.sql.*;

public class WishlistDatabase {
    Wishes wishes;
    Wishlist wishlist;

    public void insertWish(Wishes wish)throws SQLException{
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "Emperiusvalor1!")){
            //Statement st = con.createStatement();
            PreparedStatement PS = con.prepareStatement("INSERT INTO wishobjects " + "VALUES (?, ?, ?, ?, ?)");
            PS.setInt(1, wish.getID());
            PS.setString(2, wish.getName());
            PS.setString(3, String.valueOf(wish.getPrice()));
            PS.setString(4, wish.getDescription());
            PS.setString(5, String.valueOf(wish.getQuantity()));
            PS.executeUpdate();

            int id = wish.getID();
            //st.executeUpdate("INSERT INTO wishobjects " + "VALUES (id, 'Phone', '8000', 'Iphone', '1')");
        }
    }
}

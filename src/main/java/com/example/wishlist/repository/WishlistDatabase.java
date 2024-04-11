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

    public void deleteWish()throws SQLException{
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "Emperiusvalor1!")){
            //Statement st = con.createStatement();
            PreparedStatement PS = con.prepareStatement("DELETE FROM wishobjects WHERE wishName='Bil'");
            PS.executeUpdate();
        }
    }

    public Wishes recieveWish() throws SQLException{
        wishes = new Wishes();
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "Emperiusvalor1!")){
            //Statement st = con.createStatement();
            Statement statement = con.createStatement();
            String selectSQL = "SELECT * FROM wishobjects";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while(resultSet.next()){
                wishes.setID(resultSet.getInt("wishID"));
                wishes.setName(resultSet.getString("wishName"));
                wishes.setQuantity(resultSet.getInt("wishAmount"));
                wishes.setDescription(resultSet.getString("wishDescription"));
                wishes.setPrice(resultSet.getInt("wishPrice"));
            }
        }
        return wishes;
    }


}

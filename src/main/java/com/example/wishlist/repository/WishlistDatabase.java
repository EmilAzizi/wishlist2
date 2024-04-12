package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;

import java.sql.*;
import java.util.ArrayList;

public class WishlistDatabase {
    Wish wishes;
    Wishlist wishlist;

    public void insertWish(Wish wish)throws SQLException{
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "As3146594250")){
            PreparedStatement PS = con.prepareStatement("INSERT INTO wishobjects" + "VALUES (?, ?, ?, ?, ?)");
            PS.setInt(1, wish.getID());
            PS.setString(2, wish.getName());
            PS.setString(3, String.valueOf(wish.getPrice()));
            PS.setString(4, wish.getDescription());
            PS.setString(5, String.valueOf(wish.getQuantity()));
            PS.executeUpdate();
        }
    }

    public void deleteWish(Wish wish)throws SQLException{
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "As3146594250")){
            PreparedStatement PS = con.prepareStatement("DELETE FROM wishobjects WHERE wishName=?");
            PS.setString(1, wish.getName());
            PS.executeUpdate();
        }
    }

    public Wishlist recieveWish(String wishListName) throws SQLException{
        wishlist = new Wishlist();
        ArrayList<Wish> wishlists = new ArrayList<>();
        //wishlist.setName();
        wishlist.setWishList(wishlists);
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "As3146594250")){
            Statement statement = con.createStatement();
            String selectSQL = "SELECT * FROM wishobjects";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while(resultSet.next()){
                int id = resultSet.getInt("wishID");
                String name = resultSet.getString("wishName");
                int amount = resultSet.getInt("wishAmount");
                String description = resultSet.getString("wishDescription");
                int price = resultSet.getInt("wishPrice");
                Wish wish = new Wish(id, name, price, amount, description);
                //int ID, String name, int price, int quantity, String description
                wishlists.add(wish);
            }
        }
        return wishlist;
    }

    public Wishlist createWishList(Wishlist wishlistFromUser) throws SQLException{
        Wishlist wishlist = new Wishlist();
        ArrayList<Wish> wishlists = new ArrayList<>();
        wishlist.setName(wishlistFromUser.getName());
        wishlist.setWishList(wishlists);

        String tableName = wishlistFromUser.getName().replaceAll("\\s", "");

        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "As3146594250")){
            PreparedStatement statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "wishID int," +
                    "wishName varchar(255)," +
                    "wishPrice varchar(255)," +
                    "wishDescription varchar(255)," +
                    "wishAmount varchar(255))");
            statement.executeUpdate();
        }
        return wishlist;
    }

    public void checkForChangedNames(ArrayList<Wishlist> wishlists) throws SQLException{
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "As3146594250")){
            String tableName;
            for(Wishlist WL : wishlists){
                tableName = WL.getName();
                if(WL.getName() != tableName){}
            }
        }
    }


}

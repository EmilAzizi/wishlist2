package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistDatabase {
    Wish wishes;
    Wishlist wishlist;

    List<Wishlist> originalList = new ArrayList<>();

    public void insertWish(Wish wish)throws SQLException{
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "Emperiusvalor1!")){
            PreparedStatement PS = con.prepareStatement("INSERT INTO wishobjects (wishName, wishPice, wishDescription, wishAmount)" +
                    "VALUES (?, ?, ?, ?);");
            PS.setString(1, wish.getName());
            PS.setString(2, String.valueOf(wish.getPrice()));
            PS.setString(3, wish.getDescription());
            PS.setString(4, String.valueOf(wish.getAmount()));
            PS.executeUpdate();
        }
    }

    public void deleteWish(Wish wish)throws SQLException{
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "Emperiusvalor1!")){
            PreparedStatement PS = con.prepareStatement("DELETE FROM wishobjects WHERE wishName=?");
            PS.setString(1, wish.getName());
            PS.executeUpdate();
        }
    }

    public Wish createWish(Wishlist wishlist, Wish wishFromUser) throws SQLException{
        insertWish(wishFromUser);
        Wish wish = wishFromUser;
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "Emperiusvalor1!")){
            Statement statement = con.createStatement();
            String selectSQL = "SELECT * FROM " + wishlist.getName();
            ResultSet resultSet = statement.executeQuery(selectSQL);
            while(resultSet.next()){
                int id = resultSet.getInt("wishID");
                String name = resultSet.getString("wishName");
                int amount = resultSet.getInt("wishAmount");
                String description = resultSet.getString("wishDescription");
                int price = resultSet.getInt("wishPrice");
                //int ID, String name, int price, int quantity, String description;
            }
        }
        return wish;
    }

    public Wishlist createWishList(Wishlist wishlistFromUser) throws SQLException{
        Wishlist wishlist = new Wishlist();
        ArrayList<Wish> wishlists = new ArrayList<>();
        wishlist.setName(wishlistFromUser.getName());
        wishlist.setWishList(wishlists);
        int ID = 0;

        String tableName = wishlistFromUser.getName().replaceAll("\\s", "");

        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "Emperiusvalor1!")){
            PreparedStatement statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "wishID int," +
                    "wishName varchar(255)," +
                    "wishPrice varchar(255)," +
                    "wishDescription varchar(255)," +
                    "wishAmount varchar(255))");
            statement.executeUpdate();
        }
        originalList.add(wishlist);
        for(Wishlist wishlist1 : originalList){
            wishlist1.setID(ID);
            ID++;
        }
        return wishlist;
    }

    public void checkForChangedNames(Wishlist wishlistToChangeName, String newName) throws SQLException{
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "Emperiusvalor1!")){
            String oldName = "";
            for(Wishlist WL : originalList){
                if(WL.getID() == wishlistToChangeName.getID()){
                    oldName = WL.getName();
                    WL.setName(newName);
                    break;
                }
            }
            String sql = "ALTER TABLE " + oldName.replaceAll("\\s", "") + " RENAME TO " + newName.replaceAll("\\s", "");

            try(Statement statement = con.createStatement()){
                statement.executeUpdate(sql);
            }
        }
    }

    public void removeWishlistFromDB(Wishlist wishlist) throws SQLException {
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wishes","root", "Emperiusvalor1!")){
            for(Wishlist wishlist1 : originalList){
                if(wishlist1.getID() == wishlist.getID()){
                    originalList.remove(wishlist1);
                    String sql = "DROP TABLE " + wishlist.getName();
                    try(Statement statement = con.createStatement()){
                        statement.executeUpdate(sql);
                    }
                    System.out.println(wishlist1.getName() + " " + wishlist1.getID());
                    break;
                }
            }
        }
    }


}

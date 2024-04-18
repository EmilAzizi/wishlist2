package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class WishlistRepository {

    @Value("${spring.datasource.url}")
            private String db_url;
    @Value("${spring.datasource.username}")
            private String db_username;
    @Value("${spring.datasource.password}")
            private String db_password;

    ArrayList<Wishlist> wishlists = new ArrayList<>();
    List<Wishlist> originalList = new ArrayList<>();

    public WishlistRepository(){
    }

    public void createWishList(Wishlist wishlist) throws SQLException {
        int id = 0;
        Wishlist wishlistToCreate = createWishListInDB(wishlist);

        if(!wishlists.contains(wishlistToCreate)){
            wishlists.add(wishlistToCreate);
        }

        for(Wishlist WL : wishlists){
            WL.setID(id);
            id++;
        }
    }

    public Wishlist findByID(int ID){
        Wishlist wishlistToFind = null;
        for(Wishlist wishlist : wishlists){
            if(wishlist.getID() == ID){
                wishlistToFind = wishlist;
            }
        }

        if(wishlistToFind != null){
            return wishlistToFind;
        } else {
            return null;
        }
    }

    public List<Wishlist> getAll(){
        return wishlists;
    }

    public void updateWishlist(Wishlist wishlistToUpdate, String newName) throws SQLException{
        for(Wishlist WL : wishlists){
            if(wishlistToUpdate.getID() == WL.getID()){
                checkForChangedNames(wishlistToUpdate, newName);
                WL.setName(newName);
            }
        }
    }

    public void deleteWishlist(int ID) throws SQLException {
        Wishlist wishlistToBeRemoved = null;
        if(!wishlists.isEmpty()){
            for(Wishlist wishlist : wishlists){
                if(wishlist.getID() == ID){
                    wishlistToBeRemoved = wishlist;
                }
            }
        } else {
        }
        if(!wishlistToBeRemoved.equals(null)){
            wishlists.remove(wishlistToBeRemoved);
            removeWishlistFromDB(wishlistToBeRemoved);
        }
    }

    public void addWishToWishlist(int ID, Wish wishFromUser) throws SQLException {
        for(Wishlist wishlist : wishlists){
            if(wishlist.getID() == ID){
                Wish wishToBeAdded = createWish(wishlist, wishFromUser);
                wishlist.getWishList().add(wishToBeAdded);
            }
        }
    }

    public Wish findWishByID(int wishlistID, int wishID){
        Wish wishToBeUpdated = null;
        Wishlist wishlistToJumpInto = null;
        for(Wishlist wishlist : wishlists){
            if(wishlist.getID() == wishlistID){
                wishlistToJumpInto = wishlist;
            }
        }
        for(Wish wish : wishlistToJumpInto.getWishList()){
            if(wish.getID() == wishID){
                wishToBeUpdated = wish;
            }
        }
        if(!wishToBeUpdated.equals(null)){
            return wishToBeUpdated;
        } else {
            return null;
        }
    }

    public void updateWish(int wishlistID, int wishID, Wish wishToUpdate) throws SQLException {
        Wishlist wishlistToSearch = null;
        for(Wishlist wishlist : wishlists){
            if(wishlist.getID() == wishlistID){
                wishlistToSearch = wishlist;
            }
        }
        for(Wish wish : wishlistToSearch.getWishList()){
            if(wish.getID() == wishID){
                wish.setName(wishToUpdate.getName());
                wish.setDescription(wishToUpdate.getDescription());
                wish.setAmount(wishToUpdate.getAmount());
                wish.setPrice(wishToUpdate.getPrice());
                updateWish(wish, wishlistToSearch);
            }
        }
    }
    public void deleteWish(int wishlistID, int wishID) throws SQLException {
        Wish wishToBeRemoved = null;
        Wishlist wishlistToSearch = null;
        for(Wishlist wishlist : wishlists){
            if(wishlist.getID() == wishlistID){
                wishlistToSearch = wishlist;
            }
        }
        for(Wish wishToFind : wishlistToSearch.getWishList()){
            if(wishToFind.getID() == wishID){
                wishToBeRemoved = wishToFind;
            }
        }
        if(!wishToBeRemoved.equals(null)){
            wishlistToSearch.getWishList().remove(wishToBeRemoved);
            deleteWish(wishToBeRemoved, wishlistToSearch);
        }
    }

    public void insertWish(Wish wish, String wishNameFromUser)throws SQLException{
        try(Connection con = DriverManager.getConnection(db_url,db_username, db_password)){
            String tableName = "";
            if (wishNameFromUser.contains(" ")) {
                tableName = wishNameFromUser.replaceAll("\\s+", "");
            } else {
                tableName = wishNameFromUser;
            }
            PreparedStatement PS = con.prepareStatement("INSERT INTO " + tableName + " (wishName, wishPrice, wishDescription, wishAmount)" + "VALUES (?, ?, ?, ?);");
            PS.setString(1, wish.getName());
            PS.setString(2, String.valueOf(wish.getPrice()));
            PS.setString(3, wish.getDescription());
            PS.setString(4, String.valueOf(wish.getAmount()));
            PS.executeUpdate();
        }
    }

    public void deleteWish(Wish wish, Wishlist wishlist)throws SQLException{
        try(Connection con = DriverManager.getConnection(db_url,db_username, db_password)){
            String tableName = "";
            if (wishlist.getName().contains(" ")) {
                tableName = wishlist.getName().replaceAll("\\s+", "");
            } else {
                tableName = wishlist.getName();
            }
            PreparedStatement PS = con.prepareStatement("DELETE FROM " + tableName + " WHERE wishID=?");
            PS.setInt(1, wish.getID());
            PS.executeUpdate();
        }
    }

    public Wish createWish(Wishlist wishlist, Wish wishFromUser) throws SQLException{
        insertWish(wishFromUser, wishlist.getName());
        Wish wish = wishFromUser;
        try(Connection con = DriverManager.getConnection(db_url,db_username, db_password)){
            Statement statement = con.createStatement();
            String tableName = "";
            if (wishlist.getName().contains(" ")) {
                tableName = wishlist.getName().replaceAll("\\s+", "");
            } else {
                tableName = wishlist.getName();
            }
            String selectSQL = "SELECT * FROM " + tableName;
            ResultSet resultSet = statement.executeQuery(selectSQL);
            while(resultSet.next()){
                int ID = resultSet.getInt("wishID");
                String name = resultSet.getString("wishName");
                int amount = resultSet.getInt("wishAmount");
                String description = resultSet.getString("wishDescription");
                int price = resultSet.getInt("wishPrice");

                wish.setID(ID);
                wish.setName(name);
                wish.setPrice(price);
                wish.setDescription(description);
                wish.setAmount(amount);
            }
        }
        return wish;
    }
    public Wishlist createWishListInDB(Wishlist wishlistFromUser) throws SQLException {
        Wishlist wishlist = new Wishlist();
        ArrayList<Wish> wishlists = new ArrayList<>();
        wishlist.setName(wishlistFromUser.getName());
        wishlist.setWishList(wishlists);
        String tableName = "";

        try (Connection con = DriverManager.getConnection(db_url, db_username, db_password)) {
            if (wishlistFromUser.getName().contains(" ")) {
                tableName = wishlistFromUser.getName().replaceAll("\\s+", "");
            } else {
                tableName = wishlistFromUser.getName();
            }

            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "wishID int AUTO_INCREMENT PRIMARY KEY," +
                    "wishName varchar(255)," +
                    "wishPrice varchar(255)," +
                    "wishDescription varchar(255)," +
                    "wishAmount varchar(255))";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.executeUpdate();
        }
        originalList.add(wishlist);
        return wishlist;
    }


    public void checkForChangedNames(Wishlist wishlistToChangeName, String newName) throws SQLException{
        try(Connection con = DriverManager.getConnection(db_url,db_username, db_password)){
            String oldName = "";
            for(Wishlist WL : originalList){
                if(WL.getID() == wishlistToChangeName.getID()){
                    oldName = WL.getName();
                    WL.setName(newName);
                    break;
                }
            }
            String sql = "ALTER TABLE " + oldName.replaceAll("\\s+", "") + " RENAME TO " + newName.replaceAll("\\s+", "");

            try(Statement statement = con.createStatement()){
                statement.executeUpdate(sql);
            }
        }
    }

    public void removeWishlistFromDB(Wishlist wishlist) throws SQLException {
        try (Connection con = DriverManager.getConnection(db_url, db_username, db_password)) {
            String tableName = "";
            for (Wishlist wishlist1 : originalList) {
                if (wishlist1.getID() == wishlist.getID()) {
                    if (wishlist1.getName().contains(" ")) {
                        tableName = wishlist.getName().replaceAll("\\s+", "");
                    } else {
                        tableName = wishlist1.getName();
                    }
                    originalList.remove(wishlist1);
                    String sql = "DROP TABLE " + tableName;
                    try (Statement statement = con.createStatement()) {
                        statement.executeUpdate(sql);
                    }
                    break;
                }
            }
        }
    }

    public void updateWish(Wish wishToUpdate, Wishlist wishlist) throws SQLException {
        try(Connection con = DriverManager.getConnection(db_url,db_username, db_password)){
            String tableName = "";
            if (wishlist.getName().contains(" ")) {
                tableName = wishlist.getName().replaceAll("\\s+", "");
            } else {
                tableName = wishlist.getName();
            }
            PreparedStatement statement = con.prepareStatement("UPDATE " + tableName +
                    " SET wishName = ?, wishPrice = ?, wishDescription = ?, wishAmount = ? WHERE wishID = " + wishToUpdate.getID());
            statement.setString(1, wishToUpdate.getName());
            statement.setString(2, String.valueOf(wishToUpdate.getPrice()));
            statement.setString(3, wishToUpdate.getDescription());
            statement.setString(4, String.valueOf(wishToUpdate.getAmount()));
            statement.executeUpdate();
        }
    }
}

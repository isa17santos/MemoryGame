package com.example.memorygame;


import static com.example.memorygame.MemoryGameDatabaseHelper.COLUMN_COINS;
import static com.example.memorygame.MemoryGameDatabaseHelper.COLUMN_USER_ID;
import static com.example.memorygame.MemoryGameDatabaseHelper.COLUMN_USERNAME;

import android.annotation.SuppressLint;

import static com.example.memorygame.MemoryGameDatabaseHelper.TABLE_USERS;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO {
    // this class handles all crud operations for Users' table

    private SQLiteDatabase db;

    public UserDAO(SQLiteDatabase db) {
        this.db = db;
    }

    // Insert a user into the database
    public long insertUser(String username, String password, int coins) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("coins", coins);
        return db.insert("Users", null, values);
    }

    // Method to authenticate user credentials
    public boolean authenticateUser(String username, String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE username = ? AND password = ?",
                new String[]{username, password});

        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        return isAuthenticated;
    }

    // Query all users
    public Cursor getAllUsers() {
        return db.query(
                "Users", // Table name
                null,    // All columns
                null,    // No WHERE clause
                null,    // No arguments
                null,    // No grouping
                null,    // No having
                "username ASC" // Sort by username
        );
    }

    // Get coins of user
    @SuppressLint("Range")
    public int getCoins(int userId){
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_COINS + " FROM Users WHERE " + COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});

        int coins = -1;

        if(cursor != null && cursor.moveToFirst()){
            coins = cursor.getInt(cursor.getColumnIndex(COLUMN_COINS));
            cursor.close();
        }
        return coins;
    }

    @SuppressLint("Range")
    public int getLoggedInUserId(String username) {
        // Replace with your actual method to get username
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USER_ID + " FROM Users WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});

        int loggedInUserId = -1; // Default value if user not found

        if (cursor != null && cursor.moveToFirst()) {
            loggedInUserId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
            cursor.close();
        }

        return loggedInUserId;
    }

    // Increment coins
    public int incrementCoins(int userId, int newCoins) {
        ContentValues values = new ContentValues();
        values.put("coins", newCoins+1);
        return db.update("Users", values, "id = ?", new String[]{String.valueOf(userId)});
    }

    // Decrement coins
    public int decrementCoins(int userId, int newCoins) {
        ContentValues values = new ContentValues();
        values.put("coins", newCoins-1);
        return db.update("Users", values, "id = ?", new String[]{String.valueOf(userId)});
    }

    // Delete a user
    public int deleteUser(int userId) {
        return db.delete("Users", "id = ?", new String[]{String.valueOf(userId)});
    }

}

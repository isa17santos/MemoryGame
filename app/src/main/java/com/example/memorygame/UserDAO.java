package com.example.memorygame;


import static com.example.memorygame.MemoryGameDatabaseHelper.COLUMN_COINS;
import static com.example.memorygame.MemoryGameDatabaseHelper.COLUMN_USER_ID;
import static com.example.memorygame.MemoryGameDatabaseHelper.COLUMN_USERNAME;

import android.annotation.SuppressLint;

import static com.example.memorygame.MemoryGameDatabaseHelper.TABLE_USERS;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    @SuppressLint("Range")
    public int incrementCoins(int userId) {
        // 1. Get current coins
        Cursor cursor = db.rawQuery("SELECT coins FROM Users WHERE id = ?", new String[]{String.valueOf(userId)});
        int currentCoins = 0;
        if (cursor != null && cursor.moveToFirst()) {
            currentCoins = cursor.getInt(cursor.getColumnIndex("coins"));
            cursor.close();
        }

        // 2. Increment coins
        int newCoins = currentCoins + 1;

        // 3. Update in database
        ContentValues values = new ContentValues();
        values.put("coins", newCoins);
        db.update("Users", values, "id = ?", new String[]{String.valueOf(userId)});
        Log.d("UserDAO", "Updated coins for user with ID " + userId + " to " + newCoins);

        // 4. Return new coins value
        return newCoins;
    }

    // Decrement coins
    @SuppressLint("Range")
    public int decrementCoins(int userId) {
        // 1. Get current coins
        Cursor cursor = db.rawQuery("SELECT coins FROM Users WHERE id = ?", new String[]{String.valueOf(userId)});
        int currentCoins = 0;
        if (cursor != null && cursor.moveToFirst()) {
            currentCoins = cursor.getInt(cursor.getColumnIndex("coins"));
            cursor.close();
        }

        // 2. Decrement coins (with a check to prevent negative values)
        int newCoins = Math.max(0, currentCoins - 1); // Ensure coins don't go below 0

        // 3. Update in database
        ContentValues values = new ContentValues();
        values.put("coins", newCoins);
        db.update("Users", values, "id = ?", new String[]{String.valueOf(userId)});
        Log.d("UserDAO", "Updated coins for user with ID " + userId + " to " + newCoins);

        // 4. Return new coins value
        return newCoins;
    }

    // Delete a user
    public int deleteUser(int userId) {
        return db.delete("Users", "id = ?", new String[]{String.valueOf(userId)});
    }

    // Delete all users
    public int deleteAllUsers() {
        return db.delete("Users", null, null);
    }

}

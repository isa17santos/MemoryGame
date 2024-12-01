package com.example.memorygame;

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

    // Update user coins
    public int updateCoins(int userId, int newCoins) {
        ContentValues values = new ContentValues();
        values.put("coins", newCoins);
        return db.update("Users", values, "id = ?", new String[]{String.valueOf(userId)});
    }

    // Delete a user
    public int deleteUser(int userId) {
        return db.delete("Users", "id = ?", new String[]{String.valueOf(userId)});
    }
}

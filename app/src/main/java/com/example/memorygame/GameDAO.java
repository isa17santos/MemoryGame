package com.example.memorygame;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GameDAO {
    // this class handles all crud operations for Games' table

    private SQLiteDatabase db;

    public GameDAO(SQLiteDatabase db) {
        this.db = db;
    }

    // Insert a game record
    public long insertGame(int attempts, int score, String time, int boardSize, int userId) {
        ContentValues values = new ContentValues();
        values.put("attempts", attempts);
        values.put("score", score);
        values.put("time", time);
        values.put("boardSize", boardSize);
        values.put("idUser", userId);
        return db.insert("Games", null, values);
    }

    // Query games for a specific user
    public Cursor getGamesByUser(int userId) {
        return db.query(
                "Games",
                null,
                "idUser = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                "score DESC" // Sort by score descending
        );
    }

    // Query games
    public Cursor getAllGamesByTime() {
        return db.query(
                "Games", // Table name
                null,    // All columns
                null,    // No WHERE clause
                null,    // No arguments
                null,    // No grouping
                null,    // No having
                "time ASC" // Sort by username
        );
    }

    // Query games
    public Cursor getAllGamesByAttempts() {
        return db.query(
                "Games", // Table name
                null,    // All columns
                null,    // No WHERE clause
                null,    // No arguments
                null,    // No grouping
                null,    // No having
                "attempts ASC" // Sort by username
        );
    }

    // Delete all games for a specific user
    public int deleteGamesByUser(int userId) {
        return db.delete("Games", "idUser = ?", new String[]{String.valueOf(userId)});
    }
}

package com.example.memorygame;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GameDAO {
    // this class handles all crud operations for Games' table

    private SQLiteDatabase db;

    public GameDAO(SQLiteDatabase db) {
        this.db = db;
    }

    // Insert a game record
    public long insertGame(int attempts, int score, String time, int boardSize, int userId, String date) {
        ContentValues values = new ContentValues();
        values.put("attempts", attempts);
        values.put("score", score);
        values.put("time", time);
        values.put("boardSize", boardSize);
        values.put("idUser", userId);
        values.put("date", date);
        return db.insert("Games", null, values);
    }

    // Query games for a specific user by date

    public Cursor getHistorico(int userId) {
        // Log the userId to ensure it's being passed correctly
        Log.d("DBQuery", "Getting historical data for userId: " + userId);

        // Modify the query to check for any issues with the ORDER BY clause
        String query = "SELECT * FROM Games WHERE idUser = ? ORDER BY date ASC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        // Check the number of rows returned
        Log.d("DBQuery", "Number of rows returned: " + cursor.getCount());

        return cursor;
    }


    // Query games for a specific user by time and boardSize
    public Cursor getPersonalScoreboardByTimeAndBoardSize(int userId, int boardSize) {
        // Log the userId to ensure it's being passed correctly
        Log.d("DBQuery", "Getting personal data for userId: " + userId);

        String query = "SELECT Users.username, Games.time, Games.boardSize " +
                "FROM Games " +
                "INNER JOIN Users ON Games.idUser = Users.id " +
                "WHERE Games.idUser = ? AND Games.boardSize = ? " +
                "ORDER BY Games.time ASC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(boardSize)});

        // Check the number of rows returned
        Log.d("DBQuery", "Number of rows returned: " + cursor.getCount());

        return db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(boardSize)});
    }

    // Query games for a specific user by attempts and boardSize
    public Cursor getPersonalScoreboardByAttemptsAndBoardSize(int userId, int boardSize) {
        // Log the userId to ensure it's being passed correctly
        Log.d("DBQuery", "Getting personal data for userId: " + userId);

        String query = "SELECT Users.username, Games.time, Games.boardSize " +
                "FROM Games " +
                "INNER JOIN Users ON Games.idUser = Users.id " +
                "WHERE Games.idUser = ? AND Games.boardSize = ? " +
                "ORDER BY Games.attempts ASC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(boardSize)});

        // Check the number of rows returned
        Log.d("DBQuery", "Number of rows returned: " + cursor.getCount());

        return db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(boardSize)});
    }

    // Query the best game by time of a certain boardSize of each user
    public Cursor getLeaderboardByTimeAndBoardSize(int boardSize) {
        String query = "SELECT Users.username, Games.time, Games.boardSize " +
                "FROM Games " +
                "INNER JOIN Users ON Games.idUser = Users.id " +
                "WHERE Games.boardSize = ? " +
                "ORDER BY Games.time ASC";
        return db.rawQuery(query, new String[]{String.valueOf(boardSize)});
    }

    // Query all games by attempts of a certain boardSize
    public Cursor getLeaderboardByAttemptsAndBoardSize(int boardSize) {
        String query = "SELECT Users.username, Games.attempts, Games.boardSize " +
                "FROM Games " +
                "INNER JOIN Users ON Games.idUser = Users.id " +
                "WHERE Games.boardSize = ? " +
                "ORDER BY Games.attempts ASC";
        return db.rawQuery(query, new String[]{String.valueOf(boardSize)});
    }

    // Delete all games for a specific user
    public int deleteGamesByUser(int userId) {
        return db.delete("Games", "idUser = ?", new String[]{String.valueOf(userId)});
    }
}

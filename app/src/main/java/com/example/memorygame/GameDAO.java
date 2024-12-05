package com.example.memorygame;

import static com.example.memorygame.MemoryGameDatabaseHelper.COLUMN_BOARD_SIZE;
import static com.example.memorygame.MemoryGameDatabaseHelper.COLUMN_COINS;
import static com.example.memorygame.MemoryGameDatabaseHelper.COLUMN_TIME;
import static com.example.memorygame.MemoryGameDatabaseHelper.COLUMN_USER_ID;

import android.annotation.SuppressLint;
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

    public Cursor getHistory(int userId) {
        // Log the userId to ensure it's being passed correctly
        Log.d("DBQuery", "Getting historical data for userId: " + userId);

        // Modify the query to check for any issues with the ORDER BY clause
        String query = "SELECT * FROM Games WHERE idUser = ? ORDER BY date DESC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        // Check the number of rows returned
        Log.d("DBQuery", "Number of rows returned: " + cursor.getCount());

        return cursor;
    }

    // Get the best time of the user in a certain boardSize
    @SuppressLint("Range")
    public String getBestTime(int userId, int boardSize) {
        // Construct the SQL query to retrieve the best time for the user
        String query = "SELECT " + COLUMN_TIME + " FROM Users JOIN Games ON Users.id = Games.idUser WHERE Users.id = ? AND " + COLUMN_BOARD_SIZE + " = ? ORDER BY " + COLUMN_TIME + " ASC";

        Log.d("GameDAO", "getBestTime: Executing query: " + query); // Log the query for debugging

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(boardSize)});

        Log.d("GameDAO", "getBestTime: Cursor count: " + (cursor != null ? cursor.getCount() : 0)); // Log the cursor count

        String bestTime = null; // Initialize time to null (indicating no best time found)

        // Check if the cursor is valid and has at least one row
        if (cursor != null && cursor.moveToFirst()) {
            // Get the time from the first row (which should be the best time due to sorting)
            bestTime = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));

            Log.d("GameDAO", "getBestTime: Best time found: " + bestTime); // Log the best time found

            cursor.close(); // Close the cursor
        } else {
            Log.d("GameDAO", "getBestTime: No best time found for user ID: " + userId); // Log if no best time is found
        }

        return bestTime; // Return the best time (or null if not found)
    }

    // Get the third best time of a specific board size
    @SuppressLint("Range")
    public String getThirdBestTime(int boardSize) {
        String query = "SELECT time FROM Games WHERE boardSize = ? ORDER BY time ASC LIMIT 1 OFFSET 2";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(boardSize)});

        String thirdBestTime = null;
        if (cursor != null && cursor.moveToFirst()) {
            thirdBestTime = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
            cursor.close();
        } else {
            Log.d("GameDAO", "getThirdBestTime: No third best time found for board size: " + boardSize);
        }

        return thirdBestTime;
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

        String query = "SELECT Users.username, Games.attempts, Games.boardSize " +
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
        String query = "SELECT Users.username, MIN(Games.time) AS time, Games.boardSize " +
                "FROM Games " +
                "INNER JOIN Users ON Games.idUser = Users.id " +
                "WHERE Games.boardSize = ? " +
                "GROUP BY Users.username " +
                "ORDER BY time ASC";
        return db.rawQuery(query, new String[]{String.valueOf(boardSize)});
    }

    // Query all games by attempts of a certain boardSize
    public Cursor getLeaderboardByAttemptsAndBoardSize(int boardSize) {
        String query = "SELECT Users.username, MIN(Games.attempts) AS attempts, Games.boardSize " +
                "FROM Games " +
                "INNER JOIN Users ON Games.idUser = Users.id " +
                "WHERE Games.boardSize = ? " +
                "GROUP BY Users.username " +
                "ORDER BY attempts ASC";
        return db.rawQuery(query, new String[]{String.valueOf(boardSize)});
    }

    // Delete all games for a specific user
    public int deleteGamesByUser(int userId) {
        return db.delete("Games", "idUser = ?", new String[]{String.valueOf(userId)});
    }
}

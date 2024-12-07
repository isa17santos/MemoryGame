package com.example.memorygame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoryGameDatabaseHelper extends SQLiteOpenHelper {
    // this class only handles the database setup (creation and if necessary drops the table and creates it again)

    // Database Name and Version
    private static final String DATABASE_NAME = "MemoryGame.db";
    private static final int DATABASE_VERSION = 5;

    // Table Names
    public static final String TABLE_USERS = "Users";
    public static final String TABLE_GAMES = "Games";
    public static final String TABLE_NOTIFICATIONS = "Notifications";

    // Users Table Columns
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_COINS = "coins";
    public static final String COLUMN_FIRST_TIME_LOGGING = "firstTimeLogging";

    // Games Table Columns
    public static final String COLUMN_GAME_ID = "id";
    public static final String COLUMN_ATTEMPTS = "attempts";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_TIME = "time"; // Format: MI:SS
    public static final String COLUMN_BOARD_SIZE = "boardSize"; // Values: 1, 2, 3, 4
    public static final String COLUMN_GAME_USER_ID = "idUser"; // Foreign Key
    public static final String COLUMN_GAME_DATE = "date"; // Format: YYYY-MM-DD

    // Notifications Table Columns
    public static final String COLUMN_NOTIFICATION_ID = "id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_HAS_BEEN_READ = "hasBeenRead";
    public static final String COLUMN_NOTIFICATION_USER_ID = "idUser"; // Foreign Key
    public static final String COLUMN_TITLE = "title";

    public MemoryGameDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_FIRST_TIME_LOGGING + " TEXT NOT NULL DEFAULT 'yes', " +
                COLUMN_COINS + " INTEGER DEFAULT 0);";

        // Create Games Table
        String createGamesTable = "CREATE TABLE " + TABLE_GAMES + " (" +
                COLUMN_GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ATTEMPTS + " INTEGER NOT NULL, " +
                COLUMN_SCORE + " INTEGER NOT NULL, " +
                COLUMN_TIME + " TEXT NOT NULL, " +
                COLUMN_BOARD_SIZE + " INTEGER CHECK(" + COLUMN_BOARD_SIZE + " IN (1,2,3)), " +
                COLUMN_GAME_USER_ID + " INTEGER NOT NULL, " +
                COLUMN_GAME_DATE + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_GAME_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "));";

        // Create Notifications Table
        String createNotificationsTable = "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
                COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_MESSAGE + " TEXT NOT NULL, " +
                COLUMN_HAS_BEEN_READ + " TEXT NOT NULL DEFAULT 'no', " +
                COLUMN_NOTIFICATION_USER_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_NOTIFICATION_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "));";

        // Execute SQL Statements
        db.execSQL(createUsersTable);
        db.execSQL(createGamesTable);
        db.execSQL(createNotificationsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > newVersion) {
            // Handle database downgrade
            onDowngrade(db, oldVersion, newVersion);
        } else {
            // Handle database upgrade
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        // Recreate the tables with the schema for version 3
        onCreate(db);
    }
}

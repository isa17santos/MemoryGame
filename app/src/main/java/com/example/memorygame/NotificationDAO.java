package com.example.memorygame;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotificationDAO {
    // this class handles all crud operations for Notifications' table

    private SQLiteDatabase db;

    public NotificationDAO(SQLiteDatabase db) {
        this.db = db;
    }

    // Insert a notification
    public long insertNotification(String title, String message, int userId) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("message", message);
        values.put("idUser", userId);
        return db.insert("Notifications", null, values);
    }

    // Query notifications for a specific user
    public Cursor getNotificationsByUser(int userId) {
        return db.query(
                "Notifications",
                null,
                "idUser = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                "id DESC" // Sort by ID descending (latest first)
        );
    }

    // Delete a notification
    public int deleteNotification(int notificationId) {
        return db.delete("Notifications", "id = ?", new String[]{String.valueOf(notificationId)});
    }
}

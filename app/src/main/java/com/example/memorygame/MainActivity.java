package com.example.memorygame;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Date;
import java.util.Locale;

import java.util.List;


import android.annotation.SuppressLint;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Handler;

import com.example.memorygame.databinding.Board3x4AnonymousBinding;
import com.example.memorygame.databinding.Board3x4UserBinding;
import com.example.memorygame.databinding.Board4x4Binding;
import com.example.memorygame.databinding.Board6x6Binding;
import com.example.memorygame.databinding.BoardsizePageBinding;
import com.example.memorygame.databinding.DashboardUserBinding;


import com.example.memorygame.databinding.EmptyFieldsPopUpBinding;
import com.example.memorygame.databinding.HistoryPageBinding;
import com.example.memorygame.databinding.InvalidLoginPopUpBinding;
import com.example.memorygame.databinding.LeavingGamePopUpBinding;
import com.example.memorygame.databinding.PopUpNotEnoughCoinsBinding;


import com.example.memorygame.databinding.GameOverPopUpBinding;
import com.example.memorygame.databinding.ScoreboardBoardsizesGlobalBinding;
import com.example.memorygame.databinding.ScoreboardGlobalPageAttempts3x4Binding;
import com.example.memorygame.databinding.ScoreboardGlobalPageAttempts4x4Binding;
import com.example.memorygame.databinding.ScoreboardGlobalPageAttempts6x6Binding;
import com.example.memorygame.databinding.ScoreboardGlobalPageTime3x4Binding;
import com.example.memorygame.databinding.ScoreboardGlobalPageTime4x4Binding;
import com.example.memorygame.databinding.ScoreboardGlobalPageTime6x6Binding;
import com.example.memorygame.databinding.ScoreboardPersonalPageAttempts3x4Binding;
import com.example.memorygame.databinding.ScoreboardPersonalPageAttempts4x4Binding;
import com.example.memorygame.databinding.ScoreboardPersonalPageAttempts6x6Binding;
import com.example.memorygame.databinding.ScoreboardPersonalPageTime3x4Binding;
import com.example.memorygame.databinding.ScoreboardPersonalPageTime4x4Binding;
import com.example.memorygame.databinding.ScoreboardPersonalPageTime6x6Binding;


public class MainActivity extends AppCompatActivity {

    // Variables

    private Board3x4AnonymousBinding binding;
    private Board3x4UserBinding binding3x4;
    private Board4x4Binding binding4x4;
    private Board6x6Binding binding6x6;
    private BoardsizePageBinding bindingSize;
    private DashboardUserBinding userBinding;
    private ScoreboardBoardsizesGlobalBinding scoreboardBoardsizesGlobalBinding;
    private ScoreboardGlobalPageTime3x4Binding scoreboardGlobalPageTime3x4Binding;
    private ScoreboardGlobalPageTime4x4Binding scoreboardGlobalPageTime4x4Binding;
    private ScoreboardGlobalPageTime6x6Binding scoreboardGlobalPageTime6x6Binding;
    private ScoreboardGlobalPageAttempts3x4Binding scoreboardGlobalPageAttempts3x4Binding;
    private ScoreboardGlobalPageAttempts4x4Binding scoreboardGlobalPageAttempts4x4Binding;
    private ScoreboardGlobalPageAttempts6x6Binding scoreboardGlobalPageAttempts6x6Binding;
    private ScoreboardPersonalPageTime3x4Binding scoreboardPersonalPageTime3x4Binding;
    private ScoreboardPersonalPageTime4x4Binding scoreboardPersonalPageTime4x4Binding;
    private ScoreboardPersonalPageTime6x6Binding scoreboardPersonalPageTime6x6Binding;
    private ScoreboardPersonalPageAttempts3x4Binding scoreboardPersonalPageAttempts3x4Binding;
    private ScoreboardPersonalPageAttempts4x4Binding scoreboardPersonalPageAttempts4x4Binding;
    private ScoreboardPersonalPageAttempts6x6Binding scoreboardPersonalPageAttempts6x6Binding;


    private HistoryPageBinding historyPageBinding;
    private GameOverPopUpBinding gameOverPopUpBinding;

    private TableLayout tableLayout;

     private PopUpNotEnoughCoinsBinding popUpNotEnoughCoinsBinding;

     private EmptyFieldsPopUpBinding emptyFieldsPopUpBinding;

     private InvalidLoginPopUpBinding invalidLoginPopUpBinding;

     private LeavingGamePopUpBinding leavingGamePopUpBinding;

    private ArrayList<MemoryCard> memoryCards = new ArrayList<>();
    MemoryCard FirstCard = null;
    private ImageView firstCardImageView = null; // Declare a variable to store the ImageView
    int matchCount = 0;
    boolean isWaiting = false;

    private TextView timerTextView;
    private final Handler handler = new Handler();
    private int seconds = 0; // Start from 0 seconds
    int minutes = 0;
    int remainingSeconds = 0;
    private Runnable updateTimerRunnable;

    //"attempts" - 1 attempt = 2 cartas viradas
    //"score":
    //1 attempt correta = +5 pontos
    //1 attempt errada = -1 pontos
    private TextView attemptsTextView;
    private TextView scoreTextView;
    private int attempts = 0;
    private int score = 0;
    int userCoins = 0;
    private String bestTime = null;
    private String thirdBestTime = null;
    private boolean initializeTimer = false;


    // Database variables

    private boolean testMode = false;

    private GameDAO gameDAO;
    private NotificationDAO notificationDAO;

    private String currentUser = null;
    private int currentUserId = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
    String currentDate = dateFormat.format(new Date());
  
    // Get the writable database
    private SQLiteDatabase db = null;

    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //------------------------------- DATABASE --------------------------------------
        // Initialize the database helper
        MemoryGameDatabaseHelper dbHelper = new MemoryGameDatabaseHelper(this);

        try {
            db = dbHelper.getWritableDatabase(); // Open or create the database
            Log.d("Database", "Database initialized successfully.");

            // Create a new UserDAO instance
            userDAO = new UserDAO(db);
            gameDAO = new GameDAO(db);
            notificationDAO = new NotificationDAO(db);


            // Check if users have already been created using SharedPreferences
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean usersCreated = preferences.getBoolean("users_created", false);

            if (!usersCreated) {
                // If users haven't been created, insert users
                insertTestUsers(userDAO);

                // After creating users, set the flag to true
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("users_created", true);
                editor.apply();

                Log.d("UserCreation", "Users created successfully.");
            } else {
                Log.d("UserCreation", "Users already created, skipping.");
            }

            // Retrieve and log all users
            Cursor cursor = userDAO.getAllUsers();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
                    @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                    @SuppressLint("Range") int coins = cursor.getInt(cursor.getColumnIndex("coins"));
                    Log.d("User", "ID: " + id + ", Username: " + username + ", Coins: " + coins);
                } while (cursor.moveToNext());
            } else {
                Log.d("User", "No users found in the database.");
            }

            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }

        } catch (Exception e) {
            Log.e("Database", "Error interacting with the database", e);
        }

            // For Games
            //GameDAO gameDAO = new GameDAO(db);
            //gameDAO.insertGame(5, 200, "02:30", 4, (int) userId);
            //Cursor gamesCursor = gameDAO.getGamesByUser((int) userId);
            //gamesCursor.close();

        //------------------------------- DATABASE --------------------------------------
    }

    // Method to insert test users
    private void insertTestUsers(UserDAO userDAO) {
        userDAO.insertUser("isa", "isa123", 20);
        userDAO.insertUser("bruno", "bruno123", 20);
        userDAO.insertUser("carolina", "carolina123", 20);
        userDAO.insertUser("duarte", "duarte123", 20);
        userDAO.insertUser("test", "test123", 100);

    }

    private void displayNotifications() {
        Cursor cursor = notificationDAO.getNotificationsByUser(currentUserId);
        List<Notification> notifications = new ArrayList<>();

        notificationDAO.insertNotification("Seja bem vindo ao Memory Game! Comece a jogar agora e diverta-se!", currentUserId);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndexOrThrow("idUser"));
                notifications.add(new Notification("Notification " + id, message));
                Log.d("Notifications", "ID: " + id + ", Message: " + message + ", User ID: " + userId);
                System.out.println("ID: " + id + ", Message: " + message + ", User ID: " + userId);
            } while (cursor.moveToNext());
        }
        cursor.close();

        RecyclerView recyclerView = findViewById(R.id.recyclerView_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NotificationAdapter notificationAdapter = new NotificationAdapter(notifications);
        recyclerView.setAdapter(notificationAdapter);

    }

    private void notEnoughtCoins()
    {
        // Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set title, message, and buttons
        builder.setTitle("Not enough coins!");

        // Positive Button (e.g., OK)
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Handle OK button click
            dialog.dismiss(); // Close the pop-up
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startTimer() {
        // Define the Runnable that updates the TextView every second
        updateTimerRunnable = new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                seconds++; // Increment seconds by 1

                // Calculate minutes and seconds
                minutes = seconds / 60; // Calculate minutes
                remainingSeconds = seconds % 60; // Calculate remaining seconds

                // Update the TextView with formatted time (MM:SS)
                timerTextView.setText(String.format("%02d:%02d", minutes, remainingSeconds));

                // Post the Runnable again after 1 second (1000ms)
                handler.postDelayed(this, 1000);
            }
        };

        // Start the Runnable for the first time
        handler.post(updateTimerRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove the Runnable to stop the timer when the activity is paused
        handler.removeCallbacks(updateTimerRunnable);
    }

    private void stopTimer() {
        // Remove the Runnable from the message queue to stop it from executing
        handler.removeCallbacks(updateTimerRunnable);
    }

    public void moveTo_login_page(View view) {
        setContentView(R.layout.activity_main);
    }

    public void moveTo_boardsize_page(View view) {
        bindingSize = BoardsizePageBinding.inflate(getLayoutInflater());
        setContentView(bindingSize.getRoot());

        View button3x4 = bindingSize.button3x4Board;
        View button4x4 = bindingSize.button4x4Board;
        View button6x6 = bindingSize.button6x6Board;
        TextView coins = bindingSize.numCoins;
        coins.setText(String.valueOf(userCoins));

        button3x4.setOnClickListener(v -> {

            if(userCoins <= 0) { //   not enough coins
                showNotEnoughCoinsPopUp();
            }else{
                moveTo_board3x4_user(button3x4);
            }
        });

        button4x4.setOnClickListener(v -> {
            if(userCoins <= 0){//   not enough coins
                showNotEnoughCoinsPopUp();
            }

            else{
                buyingThings(button4x4, coins);
                moveTo_board4x4(button4x4);
            }
        });

        button6x6.setOnClickListener(v -> {

            if(userCoins <= 0){ //   not enough coins
                showNotEnoughCoinsPopUp();
            }

            else{
                buyingThings(button6x6, coins);
                moveTo_board6x6(button6x6);
            }
        });
    }

    public void buyingThings(View view, TextView coins)
    {
        // Get the current value of the TextView
        String text = coins.getText().toString();
        userCoins = Integer.parseInt(text);

        if (userCoins <= 0) //not enough coins
        {

            showNotEnoughCoinsPopUp();

        }
        else {
            // Decrement the value
            userDAO.decrementCoins(currentUserId, userCoins);
        }

        // Update the TextView with the new value
        coins.setText(String.valueOf(userCoins));
    }

    public void moveTo_history_page(View view) {
        historyPageBinding = HistoryPageBinding.inflate(getLayoutInflater());
        setContentView(historyPageBinding.getRoot());

        Cursor cursor = gameDAO.getHistory(currentUserId);
        GridLayout gridLayout = historyPageBinding.GameHistory;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                    @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("idUser"));
                @SuppressLint("Range") String score = cursor.getString(cursor.getColumnIndex("score"));
                @SuppressLint("Range") String attempts = cursor.getString(cursor.getColumnIndex("attempts"));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));

                Log.d("Game", "Adding Row - Username: " + user + ", Score: " + score + ", Attempts: " + attempts + ", Time: " + time + ", Board Size: " + board + ", Date: " + date);

                TextView scoreTextView = createTextView(score);
                TextView attemptsTextView = createTextView(attempts);
                TextView timeTextView = createTextView(attempts);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, score, rowIndex, 0, 1f, 1f); // Score in column 0
                addViewToGridLayout(gridLayout, attempts, rowIndex, 1, 1f, 1f); // Attempts in column 0
                addViewToGridLayout(gridLayout, time, rowIndex, 2, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 3, 1f, 1f); // Board size in column 2


                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void moveTo_scoreboard_page_user(View view) {
        setContentView(R.layout.scoreboard_page_user);
    }

    public void moveTo_scoreboard_boardsizes_global(View view) {
        scoreboardBoardsizesGlobalBinding = ScoreboardBoardsizesGlobalBinding.inflate(getLayoutInflater());
        setContentView(scoreboardBoardsizesGlobalBinding.getRoot());

        View backArrow = scoreboardBoardsizesGlobalBinding.backArrow;

        backArrow.setOnClickListener(v -> {

            if(currentUser != null){
                moveTo_scoreboard_page_user(backArrow);
            }else{
                moveTo_dashboard_anonymous(backArrow);
            }
        });
    }
    public void moveTo_scoreboard_global_page_time_3x4(View view) {
        scoreboardGlobalPageTime3x4Binding = ScoreboardGlobalPageTime3x4Binding.inflate(getLayoutInflater());
        setContentView(scoreboardGlobalPageTime3x4Binding.getRoot());

        Cursor cursor = gameDAO.getLeaderboardByTimeAndBoardSize(1);
        GridLayout gridLayout = scoreboardGlobalPageTime3x4Binding.GlobalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + time + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(time);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, time, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2

                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void moveTo_scoreboard_global_page_time_4x4(View view) {
        scoreboardGlobalPageTime4x4Binding = ScoreboardGlobalPageTime4x4Binding.inflate(getLayoutInflater());
        setContentView(scoreboardGlobalPageTime4x4Binding.getRoot());

        Cursor cursor = gameDAO.getLeaderboardByTimeAndBoardSize(2);
        GridLayout gridLayout = scoreboardGlobalPageTime4x4Binding.GlobalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + time + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(time);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, time, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2


                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void moveTo_scoreboard_global_page_time_6x6(View view) {
        scoreboardGlobalPageTime6x6Binding = ScoreboardGlobalPageTime6x6Binding.inflate(getLayoutInflater());
        setContentView(scoreboardGlobalPageTime6x6Binding.getRoot());

        Cursor cursor = gameDAO.getLeaderboardByTimeAndBoardSize(3);
        GridLayout gridLayout = scoreboardGlobalPageTime6x6Binding.GlobalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + time + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(time);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, time, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2


                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void moveTo_scoreboard_global_page_attempts_3x4(View view) {
        scoreboardGlobalPageAttempts3x4Binding = ScoreboardGlobalPageAttempts3x4Binding.inflate(getLayoutInflater());
        setContentView(scoreboardGlobalPageAttempts3x4Binding.getRoot());

        Cursor cursor = gameDAO.getLeaderboardByAttemptsAndBoardSize(1);
        GridLayout gridLayout = scoreboardGlobalPageAttempts3x4Binding.GlobalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String attempts = cursor.getString(cursor.getColumnIndex("attempts"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + attempts + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(attempts);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, attempts, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2


                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void moveTo_scoreboard_global_page_attempts_4x4(View view) {
        scoreboardGlobalPageAttempts4x4Binding = ScoreboardGlobalPageAttempts4x4Binding.inflate(getLayoutInflater());
        setContentView(scoreboardGlobalPageAttempts4x4Binding.getRoot());

        Cursor cursor = gameDAO.getLeaderboardByAttemptsAndBoardSize(2);
        GridLayout gridLayout = scoreboardGlobalPageAttempts4x4Binding.GlobalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String attempts = cursor.getString(cursor.getColumnIndex("attempts"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + attempts + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(attempts);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, attempts, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2


                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void moveTo_scoreboard_global_page_attempts_6x6(View view) {
        scoreboardGlobalPageAttempts6x6Binding = ScoreboardGlobalPageAttempts6x6Binding.inflate(getLayoutInflater());
        setContentView(scoreboardGlobalPageAttempts6x6Binding.getRoot());

        Cursor cursor = gameDAO.getLeaderboardByAttemptsAndBoardSize(3);
        GridLayout gridLayout = scoreboardGlobalPageAttempts6x6Binding.GlobalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String attempts = cursor.getString(cursor.getColumnIndex("attempts"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + attempts + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(attempts);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, attempts, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2


                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    public void moveTo_scoreboard_boardsizes_personal(View view) {
        setContentView(R.layout.scoreboard_boardsizes_personal);
    }
    public void moveTo_scoreboard_personal_page_time_3x4(View view) {

        scoreboardPersonalPageTime3x4Binding = ScoreboardPersonalPageTime3x4Binding.inflate(getLayoutInflater());
        setContentView(scoreboardPersonalPageTime3x4Binding.getRoot());

        Cursor cursor = gameDAO.getPersonalScoreboardByTimeAndBoardSize(currentUserId, 1);
        GridLayout gridLayout = scoreboardPersonalPageTime3x4Binding.PersonalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + time + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(time);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, time, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2

                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void moveTo_scoreboard_personal_page_time_4x4(View view) {
        scoreboardPersonalPageTime4x4Binding = ScoreboardPersonalPageTime4x4Binding.inflate(getLayoutInflater());
        setContentView(scoreboardPersonalPageTime4x4Binding.getRoot());

        Cursor cursor = gameDAO.getPersonalScoreboardByTimeAndBoardSize(currentUserId, 2);
        GridLayout gridLayout = scoreboardPersonalPageTime4x4Binding.PersonalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + time + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(time);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, time, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2


                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void moveTo_scoreboard_personal_page_time_6x6(View view) {
        scoreboardPersonalPageTime6x6Binding = ScoreboardPersonalPageTime6x6Binding.inflate(getLayoutInflater());
        setContentView(scoreboardPersonalPageTime6x6Binding.getRoot());

        Cursor cursor = gameDAO.getPersonalScoreboardByTimeAndBoardSize(currentUserId, 3);
        GridLayout gridLayout = scoreboardPersonalPageTime6x6Binding.PersonalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + time + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(time);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, time, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2


                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void moveTo_scoreboard_personal_page_attempts_3x4(View view) {
        scoreboardPersonalPageAttempts3x4Binding = ScoreboardPersonalPageAttempts3x4Binding.inflate(getLayoutInflater());
        setContentView(scoreboardPersonalPageAttempts3x4Binding.getRoot());

        Cursor cursor = gameDAO.getPersonalScoreboardByAttemptsAndBoardSize(currentUserId, 1);
        GridLayout gridLayout = scoreboardPersonalPageAttempts3x4Binding.PersonalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String attempts = cursor.getString(cursor.getColumnIndex("attempts"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + attempts + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(attempts);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, attempts, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2


                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void moveTo_scoreboard_personal_page_attempts_4x4(View view) {
        scoreboardPersonalPageAttempts4x4Binding = ScoreboardPersonalPageAttempts4x4Binding.inflate(getLayoutInflater());
        setContentView(scoreboardPersonalPageAttempts4x4Binding.getRoot());

        Cursor cursor = gameDAO.getPersonalScoreboardByAttemptsAndBoardSize(currentUserId, 2);
        GridLayout gridLayout = scoreboardPersonalPageAttempts4x4Binding.PersonalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String attempts = cursor.getString(cursor.getColumnIndex("attempts"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + attempts + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(attempts);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, attempts, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2


                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
    public void moveTo_scoreboard_personal_page_attempts_6x6(View view) {
        scoreboardPersonalPageAttempts6x6Binding = ScoreboardPersonalPageAttempts6x6Binding.inflate(getLayoutInflater());
        setContentView(scoreboardPersonalPageAttempts6x6Binding.getRoot());

        Cursor cursor = gameDAO.getPersonalScoreboardByAttemptsAndBoardSize(currentUserId, 3);
        GridLayout gridLayout = scoreboardPersonalPageAttempts6x6Binding.PersonalLeaderboard;
        int rowIndex = 1;

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Game", "Data found in database");
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String attempts = cursor.getString(cursor.getColumnIndex("attempts"));
                @SuppressLint("Range") int board = cursor.getInt(cursor.getColumnIndex("boardSize"));

                Log.d("Game", "Adding Row - Username: " + user + ", Time: " + attempts + ", Board Size: " + board);

                TextView usernameTextView = createTextView(user);
                TextView timeTextView = createTextView(attempts);
                TextView boardSizeTextView = createTextView(String.valueOf(board));

                // Add data to the GridLayout dynamically
                addViewToGridLayout(gridLayout, user, rowIndex, 0, 1f, 1f); // Username in column 0
                addViewToGridLayout(gridLayout, attempts, rowIndex, 1, 1f, 1f); // Time in column 1
                addViewToGridLayout(gridLayout, String.valueOf(board), rowIndex, 2, 1f, 1f); // Board size in column 2


                rowIndex++;
            } while (cursor.moveToNext());
        } else {
            Log.d("Game", "No data found in database");
        }
        if (cursor != null) {
            cursor.close();
        }
    }


    private TextView createTextView(String text) {
        // Create a new TextView
        TextView textView = new TextView(this);

        // Set the text content
        textView.setText(text);

        // Set appearance attributes
        textView.setTextSize(30); // Adjust font size
        textView.setGravity(Gravity.CENTER); // Center the text inside the TextView
        textView.setPadding(8, 8, 8, 8); // Add padding for better spacing

        return textView;
    }

    private void addViewToGridLayout(GridLayout gridLayout, String text, int row, int column, float columnWeight, float rowWeight) {
        // Create a new TextView
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(30); // Adjust text size as needed
        textView.setGravity(Gravity.CENTER); // Center the text
        textView.setPadding(8, 8, 8, 8); // Add padding for better spacing

        // Create layout parameters for the TextView
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();

        // Define row and column specifications
        params.rowSpec = GridLayout.spec(row, rowWeight); // Specify the row and its weight
        params.columnSpec = GridLayout.spec(column, columnWeight); // Specify the column and its weight

        // Apply layout parameters to the TextView
        textView.setLayoutParams(params);

        // Add the TextView to the GridLayout
        gridLayout.addView(textView);
    }

    public void moveTo_dashboard_user(View view){
        userBinding = DashboardUserBinding.inflate(getLayoutInflater());
        TextView coins = userBinding.numCoins;
        coins.setText(String.valueOf(userCoins));
        setContentView(userBinding.getRoot());
    }

    public void login(View view) {

        EditText usernameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.password_input);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        // Know who is logged in
        currentUser = username;
        currentUserId = userDAO.getLoggedInUserId(username);
        userCoins = userDAO.getCoins(currentUserId);

        Log.d("User", "User ID: " + currentUserId + ", Username: " + currentUser + ", Coins: " + userCoins);

        if (username.isEmpty() || password.isEmpty()) { // Empty fields
            showEmptyFieldsPopUp();
        } else { // written fields
            boolean isAuthenticated = userDAO.authenticateUser(username, password);
            if (isAuthenticated) { // Login successful
                if (username.equals("test") && password.equals("test123")) {
                  testMode = true;
                  setContentView(R.layout.dashboard_user);
                  userBinding = DashboardUserBinding.inflate(getLayoutInflater());
                  TextView coins = userBinding.numCoins;
                  coins.setText(String.valueOf(userCoins));
                  setContentView(userBinding.getRoot());
                } else {
                    testMode = false;
                    setContentView(R.layout.dashboard_user);
                    userBinding = DashboardUserBinding.inflate(getLayoutInflater());
                    TextView coins = userBinding.numCoins;
                    coins.setText(String.valueOf(userCoins));
                    setContentView(userBinding.getRoot());
                }

            } else { // Login failed
                showInvalidLoginPopUp();
            }
        }
    }

    public void moveTo_dashboard_anonymous(View view) { setContentView(R.layout.dashboard_anonymous); }

    public void moveTo_notification_page(View view) {
        setContentView(R.layout.notification_page);
        displayNotifications();
    }

    public ArrayList<MemoryCard> CreateMemoryCards(int size) {
        ArrayList<Integer> imageResources = new ArrayList<>();

        if(size == 12) {
            imageResources.add(R.drawable.candycane);
            imageResources.add(R.drawable.christmas);
            imageResources.add(R.drawable.christmastree);
            imageResources.add(R.drawable.christmaswreath);
            imageResources.add(R.drawable.christmasornament);
            imageResources.add(R.drawable.december);
            imageResources.add(R.drawable.candycane);
            imageResources.add(R.drawable.christmas);
            imageResources.add(R.drawable.christmastree);
            imageResources.add(R.drawable.christmaswreath);
            imageResources.add(R.drawable.christmasornament);
            imageResources.add(R.drawable.december);
        }
        else if(size == 16){
            imageResources.add(R.drawable.candycane);
            imageResources.add(R.drawable.christmas);
            imageResources.add(R.drawable.christmastree);
            imageResources.add(R.drawable.christmaswreath);
            imageResources.add(R.drawable.christmasornament);
            imageResources.add(R.drawable.december);
            imageResources.add(R.drawable.hat);
            imageResources.add(R.drawable.gingerbreadman);
            imageResources.add(R.drawable.candycane);
            imageResources.add(R.drawable.christmas);
            imageResources.add(R.drawable.christmastree);
            imageResources.add(R.drawable.christmaswreath);
            imageResources.add(R.drawable.christmasornament);
            imageResources.add(R.drawable.december);
            imageResources.add(R.drawable.hat);
            imageResources.add(R.drawable.gingerbreadman);

        }
        else if(size == 36){
            imageResources.add(R.drawable.candycane);
            imageResources.add(R.drawable.christmas);
            imageResources.add(R.drawable.christmastree);
            imageResources.add(R.drawable.christmaswreath);
            imageResources.add(R.drawable.christmasornament);
            imageResources.add(R.drawable.december);
            imageResources.add(R.drawable.hat);
            imageResources.add(R.drawable.gingerbreadman);
            imageResources.add(R.drawable.gift);
            imageResources.add(R.drawable.giftbox);
            imageResources.add(R.drawable.hotdrink);
            imageResources.add(R.drawable.mistletoe);
            imageResources.add(R.drawable.reindeer);
            imageResources.add(R.drawable.sock);
            imageResources.add(R.drawable.star);
            imageResources.add(R.drawable.snowflake);
            imageResources.add(R.drawable.penguin);
            imageResources.add(R.drawable.christmasbell);
            imageResources.add(R.drawable.candycane);
            imageResources.add(R.drawable.christmas);
            imageResources.add(R.drawable.christmastree);
            imageResources.add(R.drawable.christmaswreath);
            imageResources.add(R.drawable.christmasornament);
            imageResources.add(R.drawable.december);
            imageResources.add(R.drawable.hat);
            imageResources.add(R.drawable.gingerbreadman);
            imageResources.add(R.drawable.gift);
            imageResources.add(R.drawable.giftbox);
            imageResources.add(R.drawable.hotdrink);
            imageResources.add(R.drawable.mistletoe);
            imageResources.add(R.drawable.reindeer);
            imageResources.add(R.drawable.sock);
            imageResources.add(R.drawable.star);
            imageResources.add(R.drawable.snowflake);
            imageResources.add(R.drawable.penguin);
            imageResources.add(R.drawable.christmasbell);
        }
        else {
            throw new IllegalArgumentException("Invalid size");
        }

        ArrayList<MemoryCard> memoryCards = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            memoryCards.add(new MemoryCard(imageResources.get(i)));
        }
        return memoryCards;
    }

    public void moveTo_board3x4_anonymous(View view) {

        binding = Board3x4AnonymousBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        timerTextView = binding.timerTextViewBoard3x4Anonymous;
        attemptsTextView = binding.attemptsTextBoard3x4Anonymous;
        scoreTextView = binding.scoreTextBoard3x4Anonymous;

        setupGame(0);
    }

    public void moveTo_board3x4_user(View view) {

        binding3x4 = Board3x4UserBinding.inflate(getLayoutInflater());
        setContentView(binding3x4.getRoot());

        timerTextView = binding3x4.timerTextViewBoard3x4User;
        attemptsTextView = binding3x4.attemptsTextBoard3x4User;
        scoreTextView = binding3x4.scoreTextBoard3x4User;

        View buttonHint = binding3x4.buttonHintBoard3x4User;
        TextView coins = binding3x4.numCoins;
        coins.setText(String.valueOf(userCoins));

        buttonHint.setOnClickListener(v -> {
            buyHint(v, coins, 1);
        });

        setupGame(1);
    }

    public void moveTo_board4x4(View view) {

        binding4x4 = Board4x4Binding.inflate(getLayoutInflater());
        setContentView(binding4x4.getRoot());

        timerTextView = binding4x4.timerTextViewBoard4x4;
        attemptsTextView = binding4x4.attemptsTextBoard4x4;
        scoreTextView = binding4x4.scoreTextBoard4x4;

        View buttonHint = binding4x4.buttonHintBoard4x4;
        TextView coins = binding4x4.numCoins;
        coins.setText(String.valueOf(userCoins));

        buttonHint.setOnClickListener(v -> {
            buyHint(v, coins, 2);
        });

        setupGame(2);
    }

    public void moveTo_board6x6(View view) {

        binding6x6 = Board6x6Binding.inflate(getLayoutInflater());
        setContentView(binding6x6.getRoot());

        timerTextView = binding6x6.timerTextViewBoard6x6;
        attemptsTextView = binding6x6.attemptsTextBoard6x6;
        scoreTextView = binding6x6.scoreTextBoard6x6;

        View buttonHint = binding6x6.buttonHintBoard6x6;
        TextView coins = binding6x6.numCoins;
        coins.setText(String.valueOf(userCoins));

        buttonHint.setOnClickListener(v -> {
            buyHint(v, coins, 3);
        });

        setupGame(3);
    }


    public void setupGame(int boardType) {
        // boardType:
        // 0 -> Board 3x4 anonymous
        // 1 -> Board 3x4 user
        // 2 -> Board 4x4 user
        // 3 -> Board 6x6 user

        if (boardType == 0) // Board 3x4 anonymous
        {
            int cardCount = binding.MemoryGrid.getChildCount();
            memoryCards = CreateMemoryCards(cardCount);

            if(!testMode)
                Collections.shuffle(memoryCards);

        }
        else if (boardType == 1) // Board 3x4 user
        {
            int cardCount = binding3x4.MemoryGrid.getChildCount();
            memoryCards = CreateMemoryCards(cardCount);

            if(!testMode)
                Collections.shuffle(memoryCards);

        }
        else if (boardType == 2) // Board 4x4 user
        {
            int cardCount = binding4x4.MemoryGrid.getChildCount();
            memoryCards = CreateMemoryCards(cardCount);

            if(!testMode)
                Collections.shuffle(memoryCards);
        }
        else if (boardType == 3) // Board 6x6 user
        {
            int cardCount = binding6x6.MemoryGrid.getChildCount();
            memoryCards = CreateMemoryCards(cardCount);

            if(!testMode)
                Collections.shuffle(memoryCards);
        }
        else // Invalid board size
        {
            throw new IllegalArgumentException("Invalid board size");
        }


        initializeMemoryCards(boardType);

        FirstCard = null;
        firstCardImageView = null;
        matchCount = 0;
        isWaiting = false;
        initializeTimer = false;
        seconds = 0;
        minutes = 0;
        remainingSeconds = 0;
        attempts = 0;
        score = 0;

    }

    private void initializeMemoryCards(int boardType) {
        // boardType:
        // 0 -> Board 3x4 anonymous
        // 1 -> Board 3x4 user
        // 2 -> Board 4x4 user
        // 3 -> Board 6x6 user


        for (int i = 0; i < memoryCards.size(); i++) {

            if (boardType == 0) // Board 3x4 anonymous
            {
                ImageView imageView = (ImageView) binding.MemoryGrid.getChildAt(i);
                imageView.setClickable(true);
                imageView.setTag(i);

                // Set click listener for each ImageView
                imageView.setOnClickListener(v -> handleCardClick((ImageView) v,0, boardType));
            }
            else if (boardType == 1) // Board 3x4 user
            {
                ImageView imageView = (ImageView) binding3x4.MemoryGrid.getChildAt(i);
                imageView.setClickable(true);
                imageView.setTag(i);

                // Set click listener for each ImageView
                imageView.setOnClickListener(v -> handleCardClick((ImageView) v,1, boardType));
            }
            else if (boardType == 2) // Board 4x4 user
            {
                ImageView imageView = (ImageView) binding4x4.MemoryGrid.getChildAt(i);
                imageView.setClickable(true);
                imageView.setTag(i);

                // Set click listener for each ImageView
                imageView.setOnClickListener(v -> handleCardClick((ImageView) v, 1, boardType));
            }
            else if (boardType == 3) // Board 6x6 user
            {
                ImageView imageView = (ImageView) binding6x6.MemoryGrid.getChildAt(i);
                imageView.setClickable(true);
                imageView.setTag(i);

                // Set click listener for each ImageView
                imageView.setOnClickListener(v -> handleCardClick((ImageView) v, 1,boardType));
            }
                // Set click listener for each ImageView

                //imageView.setOnClickListener(v -> handleCardClick((ImageView) v, 2, 4));



            else // Invalid board size
            {
                throw new IllegalArgumentException("Invalid board size");
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private void handleCardClick(ImageView imageView, int gameMode, int boardSize) {

        if (!isWaiting) {

            if(!initializeTimer){
                initializeTimer = true;
                startTimer();
            }
            int index = (int) imageView.getTag();
            MemoryCard card = memoryCards.get(index);

            Log.d("MemoryCard", "ImageView clicked at index: " + index + ", revealing card: " + card.getImageId());

            if(!card.isMatched() || !card.isFlipped()) {
                card.flipCardUp();
                imageView.setBackgroundColor(ContextCompat.getColor(imageView.getContext(), card.getColor()));
                imageView.setImageResource(card.getImageId());

                if (FirstCard == null) {
                    FirstCard = card;
                    firstCardImageView = imageView; // Store the ImageView reference
                } else {
                    if (FirstCard.getImageId() == card.getImageId()) {
                        // Matched
                        matchCount++;
                        FirstCard.setMatched(true);
                        card.setMatched(true);
                        FirstCard = null;
                        firstCardImageView = null;
                        Log.d("MemoryCard", "Matched");

                        score += 5;
                        attempts++;
                        scoreTextView.setText(String.format("%d", score));
                        attemptsTextView.setText(String.format("%d", attempts));

                        if (matchCount == memoryCards.size() / 2) {
                            // Game Over
                            Log.d("MemoryCard", "Game Over");
                            stopTimer();

                            bestTime = gameDAO.getBestTime(currentUserId, boardSize);
                            thirdBestTime = gameDAO.getThirdBestTime(boardSize);

                            // Insert a game record
                            String time = String.format("%02d:%02d", minutes, seconds);

                            long val = gameDAO.insertGame(attempts, score, time, boardSize, currentUserId, currentDate);
                            Log.d("MemoryCard", "Game record inserted with ID: " + val);
                            if (val == -1) {
                                Log.d("MemoryCard", "Game record insertion failed");
                            }

                            // Assuming 'time' and 'bestTime' are strings in the format "mm:ss"
                            String[] timeParts = time.split(":");
                            int timeInSeconds = Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);

                            if (bestTime != null) {
                                String[] bestTimeParts = bestTime.split(":");
                                int bestTimeInSeconds = Integer.parseInt(bestTimeParts[0]) * 60 + Integer.parseInt(bestTimeParts[1]);
                                Log.d("MemoryCard", "Best Time: " + bestTime);

                                // Now you can compare the times in seconds
                                if (timeInSeconds < bestTimeInSeconds) {
                                    // ... (time is less than bestTime) ...
                                    Log.d("MemoryCard", "New Record!!!!");
                                    String notificationMessage = String.format("You beat your personal best time in board %d!!", boardSize);
                                    notificationDAO.insertNotification(notificationMessage, currentUserId);

                                    userDAO.incrementCoins(currentUserId,userCoins);
                                    notificationDAO.insertNotification("A reward for proving you are the best, here's a Coin", currentUserId);
                                }
                            }

                            if (thirdBestTime != null) {
                                String[] thirdBestTimeParts = thirdBestTime.split(":");
                                int thirdBestTimeInSeconds = Integer.parseInt(thirdBestTimeParts[0]) * 60 + Integer.parseInt(thirdBestTimeParts[1]);
                                Log.d("MemoryCard", "Top 3 Time: " + thirdBestTime);
                                if (timeInSeconds < thirdBestTimeInSeconds) {
                                    Log.d("MemoryCard", "Top 3 Global Leaderboard!!!!");
                                    String notificationMessage = String.format("You are now top 3 Global on board %d!!", boardSize);
                                    notificationDAO.insertNotification(notificationMessage, currentUserId);

                                    userDAO.incrementCoins(currentUserId,userCoins);
                                    notificationDAO.insertNotification("A reward for proving you are the best, here's a Coin", currentUserId);
                                }
                            }

                            if(gameMode == 0) // anonymous
                            {
                                showGameOverPopUp(0);
                            }
                            else if(gameMode == 1) // user
                            {
                                showGameOverPopUp(1);

                            }
                            else if (gameMode == 2) // test
                            {
                                showGameOverPopUp(2);
                            }
                        }
                    } else {
                        isWaiting = true;
                        // Not Matched
                        Log.d("MemoryCard", "Not Matched");
                        imageView.postDelayed(() -> {
                            isWaiting = false;
                            card.flipCardDown();
                            imageView.setBackgroundColor(ContextCompat.getColor(imageView.getContext(), card.getColor()));
                            imageView.setImageResource(card.getImageId());
                            FirstCard.flipCardDown();
                            firstCardImageView.setBackgroundColor(ContextCompat.getColor(firstCardImageView.getContext(), FirstCard.getColor()));
                            firstCardImageView.setImageResource(FirstCard.getImageId());
                            FirstCard = null;
                            firstCardImageView = null;
                        }, 1000);

                        score -= 1;
                        if(score < 0){
                            score = 0;
                        }
                        attempts++;
                        scoreTextView.setText(String.format("%d", score));
                        attemptsTextView.setText(String.format("%d", attempts));
                    }
                }
            } else {
                Log.d("MemoryCard", "Card already flipped");
            }
        }
    }

    private void buyHint(View view, TextView coins, int boardType) {
        if(userCoins <= 0){//   not enough coins
            showNotEnoughCoinsPopUp();
        }

        else {
            buyingThings(view, coins);
            getHint(boardType);
        }
    }

    private void getHint(int boardType) {
        ImageView imageView1 = null;
        ImageView imageView2 = null;
        if (FirstCard != null && !FirstCard.isMatched()) {
            // Unflip the first card if it hasn't found its match
            FirstCard.flipCardDown();
            firstCardImageView.setBackgroundColor(ContextCompat.getColor(firstCardImageView.getContext(), FirstCard.getColor()));
            firstCardImageView.setImageResource(FirstCard.getImageId());
            FirstCard = null;
            firstCardImageView = null;
        }

        for (int i = 0; i < memoryCards.size(); i++) {
            MemoryCard card1 = memoryCards.get(i);
            if (!card1.isMatched()) {
                for (int j = i + 1; j < memoryCards.size(); j++) {
                    MemoryCard card2 = memoryCards.get(j);
                    if (!card2.isMatched() && card1.getRealImageId() == card2.getRealImageId()) {
                        if (boardType == 0) {
                            imageView1 = (ImageView) binding.MemoryGrid.getChildAt(i);
                            imageView2 = (ImageView) binding.MemoryGrid.getChildAt(j);
                        } else if (boardType == 1) {
                            imageView1 = (ImageView) binding3x4.MemoryGrid.getChildAt(i);
                            imageView2 = (ImageView) binding3x4.MemoryGrid.getChildAt(j);
                        } else if (boardType == 2) {
                            imageView1 = (ImageView) binding4x4.MemoryGrid.getChildAt(i);
                            imageView2 = (ImageView) binding4x4.MemoryGrid.getChildAt(j);
                        } else if (boardType == 3) {
                            imageView1 = (ImageView) binding6x6.MemoryGrid.getChildAt(i);
                            imageView2 = (ImageView) binding6x6.MemoryGrid.getChildAt(j);
                        }  else {
                            throw new IllegalArgumentException("Invalid board size");
                        }
                        // Flip both cards up
                        card1.flipCardUp();
                        card2.flipCardUp();
                        imageView1.setBackgroundColor(ContextCompat.getColor(imageView1.getContext(), card1.getColor()));
                        imageView1.setImageResource(card1.getImageId());
                        imageView2.setBackgroundColor(ContextCompat.getColor(imageView2.getContext(), card2.getColor()));
                        imageView2.setImageResource(card2.getImageId());

                        // Hide the cards again after a short delay
                        ImageView finalImageView = imageView1;
                        ImageView finalImageView1 = imageView2;
                        imageView1.postDelayed(() -> {
                            card1.flipCardDown();
                            finalImageView.setBackgroundColor(ContextCompat.getColor(finalImageView.getContext(), card1.getColor()));
                            finalImageView.setImageResource(card1.getImageId());
                            card2.flipCardDown();
                            finalImageView1.setBackgroundColor(ContextCompat.getColor(finalImageView1.getContext(), card2.getColor()));
                            finalImageView1.setImageResource(card2.getImageId());
                        }, 1000); // 1 second delay

                        return;
                    }
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private void showGameOverPopUp(int gameMode) {
        // gameMode values:
        // 0 -> anonymous
        // 1 -> user
        // 2 -> test

        try {
            gameOverPopUpBinding = GameOverPopUpBinding.inflate(getLayoutInflater());
            timerTextView = gameOverPopUpBinding.TimeValue;
            attemptsTextView = gameOverPopUpBinding.AttemptsValue;
            scoreTextView = gameOverPopUpBinding.ScoreValue;

            attemptsTextView.setText(String.valueOf(attempts));
            scoreTextView.setText(String.valueOf(score));
            timerTextView.setText(String.format("%02d:%02d", minutes, remainingSeconds));

            Log.d("PopupDebug", "Attempts: " + attempts + ", Time: " + minutes + ":" + remainingSeconds + ", Score: " + score);


            View close = gameOverPopUpBinding.closeButtonGameOver;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(gameOverPopUpBinding.getRoot());
            final AlertDialog dialog = builder.create(); // Make the dialog final


            close.setOnClickListener(v -> {
                dialog.dismiss(); // Close the dialog

                if(gameMode == 0 ) //  anonymous
                    moveTo_dashboard_anonymous(null);
                else if (gameMode == 1){  //  user
                    moveTo_dashboard_user(null);
                }
                else if (gameMode == 2){  //  test
                    //moveTo_testDashboard(null);
                }
            });

            dialog.show();

            // Get the dialog's window and set fixed dimensions
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(
                        (int) (400 * getResources().getDisplayMetrics().density), // Convert dp to pixels
                        (int) (400 * getResources().getDisplayMetrics().density)
                );
            }

        }
        catch (Exception e){
            Log.e("PopupError", "Error in showGameOverPopUp", e);
        }
    }

    @SuppressLint("DefaultLocale")
    private void showNotEnoughCoinsPopUp() {
        try {
            LayoutInflater inflater = getLayoutInflater();
            Log.d("InflaterDebug", "LayoutInflater: " + inflater);

            popUpNotEnoughCoinsBinding = PopUpNotEnoughCoinsBinding.inflate(getLayoutInflater());

            if (popUpNotEnoughCoinsBinding != null) {
                Log.d("BindingDebug", "Root View: " + popUpNotEnoughCoinsBinding.getRoot());
            } else {
                Log.d("BindingDebug", "Binding is NULL.");
            }

            View close = popUpNotEnoughCoinsBinding.closeButton;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setView(popUpNotEnoughCoinsBinding.getRoot());
            AlertDialog dialog = builder.create(); // Make the dialog final

            close.setOnClickListener(v -> {
               dialog.dismiss(); // Close the dialog
            });

            dialog.show();

            // Get the dialog's window and set fixed dimensions
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(
                        (int) (400 * getResources().getDisplayMetrics().density), // Convert dp to pixels
                        (int) (300 * getResources().getDisplayMetrics().density)
                );
            }
        }
        catch (Exception e) {
            Log.e("PopupError", "Error in showNotEnoughCoinsPopUp", e);
        }

    }


    @SuppressLint("DefaultLocale")
    private void showEmptyFieldsPopUp() {
        try {
            LayoutInflater inflater = getLayoutInflater();
            Log.d("InflaterDebug", "LayoutInflater: " + inflater);

            emptyFieldsPopUpBinding = EmptyFieldsPopUpBinding.inflate(getLayoutInflater());

            if (emptyFieldsPopUpBinding != null) {
                Log.d("BindingDebug", "Root View: " + emptyFieldsPopUpBinding.getRoot());
            } else {
                Log.d("BindingDebug", "Binding is NULL.");
            }

            View close = emptyFieldsPopUpBinding.closeButton;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setView(emptyFieldsPopUpBinding.getRoot());
            AlertDialog dialog = builder.create(); // Make the dialog final

            close.setOnClickListener(v -> {
                dialog.dismiss(); // Close the dialog
            });

            dialog.show();

            // Get the dialog's window and set fixed dimensions
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(
                        (int) (400 * getResources().getDisplayMetrics().density), // Convert dp to pixels
                        (int) (300 * getResources().getDisplayMetrics().density)
                );
            }
        }
        catch (Exception e) {
            Log.e("PopupError", "Error in showNotEnoughCoinsPopUp", e);
        }
    }

    @SuppressLint("DefaultLocale")
    private void showInvalidLoginPopUp() {
        try {
            LayoutInflater inflater = getLayoutInflater();
            Log.d("InflaterDebug", "LayoutInflater: " + inflater);

            invalidLoginPopUpBinding = InvalidLoginPopUpBinding.inflate(getLayoutInflater());

            if (invalidLoginPopUpBinding != null) {
                Log.d("BindingDebug", "Root View: " + invalidLoginPopUpBinding.getRoot());
            } else {
                Log.d("BindingDebug", "Binding is NULL.");
            }

            View close = invalidLoginPopUpBinding.closeButton;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setView(invalidLoginPopUpBinding.getRoot());
            AlertDialog dialog = builder.create(); // Make the dialog final

            close.setOnClickListener(v -> {
                dialog.dismiss(); // Close the dialog
            });

            dialog.show();

            // Get the dialog's window and set fixed dimensions
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(
                        (int) (400 * getResources().getDisplayMetrics().density), // Convert dp to pixels
                        (int) (300 * getResources().getDisplayMetrics().density)
                );
            }
        }
        catch (Exception e) {
            Log.e("PopupError", "Error in showNotEnoughCoinsPopUp", e);
        }

    }

    @SuppressLint("DefaultLocale")
    public void showLeavingGameLoginPopUpAnonymous(View view) {

        try {
            LayoutInflater inflater = getLayoutInflater();
            Log.d("InflaterDebug", "LayoutInflater: " + inflater);

            leavingGamePopUpBinding = LeavingGamePopUpBinding.inflate(getLayoutInflater());

            if (leavingGamePopUpBinding != null) {
                Log.d("BindingDebug", "Root View: " + leavingGamePopUpBinding.getRoot());
            } else {
                Log.d("BindingDebug", "Binding is NULL.");
            }

            View close = leavingGamePopUpBinding.closeButton;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setView(leavingGamePopUpBinding.getRoot());
            AlertDialog dialog = builder.create(); // Make the dialog final

            close.setOnClickListener(v -> {
                dialog.dismiss(); // Close the dialog

                moveTo_dashboard_anonymous(null);

            });

            dialog.show();

            // Get the dialog's window and set fixed dimensions
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(
                        (int) (400 * getResources().getDisplayMetrics().density), // Convert dp to pixels
                        (int) (300 * getResources().getDisplayMetrics().density)
                );
            }
        }
        catch (Exception e) {
            Log.e("PopupError", "Error in showNotEnoughCoinsPopUp", e);
        }

    }

    @SuppressLint("DefaultLocale")
    public void showLeavingGameLoginPopUpUser(View view) {

        try {
            LayoutInflater inflater = getLayoutInflater();
            Log.d("InflaterDebug", "LayoutInflater: " + inflater);

            leavingGamePopUpBinding = LeavingGamePopUpBinding.inflate(getLayoutInflater());

            if (leavingGamePopUpBinding != null) {
                Log.d("BindingDebug", "Root View: " + leavingGamePopUpBinding.getRoot());
            } else {
                Log.d("BindingDebug", "Binding is NULL.");
            }

            View close = leavingGamePopUpBinding.closeButton;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setView(leavingGamePopUpBinding.getRoot());
            AlertDialog dialog = builder.create(); // Make the dialog final

            close.setOnClickListener(v -> {
                dialog.dismiss(); // Close the dialog

                moveTo_dashboard_user(null);
            });

            dialog.show();

            // Get the dialog's window and set fixed dimensions
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(
                        (int) (400 * getResources().getDisplayMetrics().density), // Convert dp to pixels
                        (int) (300 * getResources().getDisplayMetrics().density)
                );
            }

        }
        catch (Exception e) {
            Log.e("PopupError", "Error in showNotEnoughCoinsPopUp", e);
        }

    }
}



package com.example.memorygame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.memorygame.databinding.Board3x4AnonymousBinding;
import com.example.memorygame.databinding.Board4x4Binding;


import java.util.ArrayList;
import java.util.Collections;

import android.annotation.SuppressLint;

import android.widget.TextView;
import android.os.Handler;

import com.example.memorygame.databinding.Board3x4AnonymousBinding;
import com.example.memorygame.databinding.Board6x6Binding;

public class MainActivity extends AppCompatActivity {

    // Variables
    private Board6x6Binding binding6x6;
    private Board3x4AnonymousBinding binding;
    private Board4x4Binding binding4x4;
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

    private boolean initializeTimer = false;

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

    }

    public void buyingThings(View view)
    {
        TextView textView = findViewById(R.id.num_coins);
        Button button = findViewById(R.id.buttonHint_board6x6);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current value of the TextView
                String text = textView.getText().toString();
                int value = Integer.parseInt(text);

                if (value <= 0) //not enough coins
                {
                    notEnoughtCoins();
                }
                else {
                    // Decrement the value
                    value-=1;
                }

                // Update the TextView with the new value
                textView.setText(String.valueOf(value));
            }
        });
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


    public void moveTo_boardsize_page(View view) {
        setContentView(R.layout.boardsize_page);
    }

    public void moveTo_history_page(View view) {
        setContentView(R.layout.history_page);
    }

    public void moveTo_scoreboard_page(View view) {
        setContentView(R.layout.scoreboard_page);
    }

    public void moveTo_dashboard_user(View view) {
        setContentView(R.layout.dashboard_user);
    }

    public void moveTo_board3x4_user(View view) {
        setContentView(R.layout.board3x4_user);
    }


    public void moveTo_dashboard_anonymous(View view) {
        setContentView(R.layout.dashboard_anonymous);
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

    public void moveTo_board6x6(View view) {

        binding6x6 = Board6x6Binding.inflate(getLayoutInflater());
        setContentView(binding6x6.getRoot());
        timerTextView = binding6x6.timerTextViewBoard6x6;
        attemptsTextView = binding6x6.attemptsTextBoard6x6;
        scoreTextView = binding6x6.scoreTextBoard6x6;

        setupGame(3);
    }

    public void moveTo_board3x4_anonymous(View view) {

        binding = Board3x4AnonymousBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        timerTextView = binding.timerTextViewBoard3x4Anonymous;
        attemptsTextView = binding.attemptsTextBoard3x4Anonymous;
        scoreTextView = binding.scoreTextBoard3x4Anonymous;

        setupGame(0);
    }

    public void moveTo_board4x4(View view) {
        binding4x4 = Board4x4Binding.inflate(getLayoutInflater());
        setContentView(binding4x4.getRoot());
        timerTextView = binding4x4.timerTextViewBoard4x4;
        attemptsTextView = binding4x4.attemptsTextBoard4x4;
        scoreTextView = binding4x4.scoreTextBoard4x4;

        setupGame(2);
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
            Collections.shuffle(memoryCards);
        }
        else if (boardType == 1) // Board 3x4 user
        {
            // TO DO
        }
        else if (boardType == 2) // Board 4x4 user
        {
            int cardCount = binding4x4.MemoryGrid.getChildCount();
            memoryCards = CreateMemoryCards(cardCount);
            Collections.shuffle(memoryCards);
        }
        else if (boardType == 3) // Board 6x6 user
        {
            int cardCount = binding6x6.MemoryGrid.getChildCount();
            memoryCards = CreateMemoryCards(cardCount);
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
        // 3 -> Board 6x5 user

        for (int i = 0; i < memoryCards.size(); i++) {

            if (boardType == 0) // Board 3x4 anonymous
            {
                ImageView imageView = (ImageView) binding.MemoryGrid.getChildAt(i);
                imageView.setClickable(true);
                imageView.setTag(i);

                // Set click listener for each ImageView
                imageView.setOnClickListener(v -> handleCardClick((ImageView) v, boardType));
            }
            else if (boardType == 1) // Board 3x4 user
            {
                // TO DO
            }
            else if (boardType == 2) // Board 4x4 user
            {
                ImageView imageView = (ImageView) binding4x4.MemoryGrid.getChildAt(i);
                imageView.setClickable(true);
                imageView.setTag(i);

                // Set click listener for each ImageView
                imageView.setOnClickListener(v -> handleCardClick((ImageView) v));
            }
            else if (boardType == 3) // Board 6x6 user
            {
                ImageView imageView = (ImageView) binding6x6.MemoryGrid.getChildAt(i);
                imageView.setClickable(true);
                imageView.setTag(i);

                // Set click listener for each ImageView
                imageView.setOnClickListener(v -> handleCardClick((ImageView) v, boardType));
            }
            else // Invalid board size
            {
                throw new IllegalArgumentException("Invalid board size");
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private void handleCardClick(ImageView imageView, int boardType) {
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
                            gameOverPopUp(boardType);
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

    @SuppressLint("DefaultLocale")
    private void gameOverPopUp(int boardType) {
        // Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set title, message, and buttons
        builder.setTitle("Game Finished!");
        builder.setMessage(String.format("Score: %d\n" + "Attempts: %d\n" + "Time: %02d:%02d", score, attempts, minutes, remainingSeconds));

        // Positive Button (e.g., OK)
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Handle OK button click
            dialog.dismiss(); // Close the pop-up
            if (boardType == 0) {
                setContentView(R.layout.dashboard_anonymous);
            } else {
                setContentView(R.layout.dashboard_user);
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


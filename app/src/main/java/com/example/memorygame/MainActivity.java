package com.example.memorygame;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;

import android.widget.TextView;
import android.os.Handler;

import com.example.memorygame.databinding.Board3x4AnonymousBinding;
import com.example.memorygame.databinding.Board3x4UserBinding;
import com.example.memorygame.databinding.Board4x4Binding;
import com.example.memorygame.databinding.Board6x6Binding;
import com.example.memorygame.databinding.BoardsizePageBinding;
import com.example.memorygame.databinding.DashboardUserBinding;

public class MainActivity extends AppCompatActivity {

    // Variables

    private Board3x4AnonymousBinding binding;
    private Board3x4UserBinding binding3x4;
    private Board4x4Binding binding4x4;
    private Board6x6Binding binding6x6;
    private BoardsizePageBinding bindingSize;
    private DashboardUserBinding userBinding;

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
    int value = 5;
    private boolean initializeTimer = false;

    private boolean testMode = false;

    List<Notification> notificationList = new ArrayList<>();

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

        notificationList.add(new Notification("Bem Vindo!", "Seja bem vindo ao Memory Game! Comece a jogar agora e diverta-se!"));

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
        setContentView(R.layout.boardsize_page);
        bindingSize = BoardsizePageBinding.inflate(getLayoutInflater());
        setContentView(bindingSize.getRoot());
        View button3x4 = bindingSize.button3x4Board;
        View button4x4 = bindingSize.button4x4Board;
        View button6x6 = bindingSize.button6x6Board;
        TextView coins = bindingSize.numCoins;
        coins.setText(String.valueOf(value));

        button3x4.setOnClickListener(v -> {
            if(value <= 0) //   not enough coins
                notEnoughtCoins();
            else{
                buyingThings(button3x4, coins);
                moveTo_board3x4_user(button3x4);
            }
        });

        button4x4.setOnClickListener(v -> {
            if(value <= 0) //   not enough coins
                notEnoughtCoins();
            else{
                buyingThings(button4x4, coins);
                moveTo_board4x4(button4x4);
            }
        });

        button6x6.setOnClickListener(v -> {
            if(value <= 0) //   not enough coins
                notEnoughtCoins();
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
        value = Integer.parseInt(text);

        if (value <= 0) //not enough coins
        {
            notEnoughtCoins();
        }
        else {
            // Decrement the value
            value-=1;
        }

        // Update the TextView with the new value
        coins.setText(String.valueOf(value));
    }

    public void moveTo_history_page(View view) {
        setContentView(R.layout.history_page);
    }

    public void moveTo_scoreboard_page_user(View view) {
        setContentView(R.layout.scoreboard_page_user);
    }

    public void moveTo_scoreboard_global_page(View view) {
        setContentView(R.layout.scoreboard_global_page);
    }

    public void moveTo_scoreboard_personal_page(View view) {
        setContentView(R.layout.scoreboard_personal_page);
    }

    public void login(View view) {
        EditText usernameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.password_input);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.equals("test") && password.equals("test123")) {
            testMode = true;
            setContentView(R.layout.dashboard_user);
            userBinding = DashboardUserBinding.inflate(getLayoutInflater());
            setContentView(userBinding.getRoot());
            TextView coins = userBinding.numCoins;
            coins.setText(String.valueOf(value));
        } else {
            testMode = false;
            setContentView(R.layout.dashboard_user);
            userBinding = DashboardUserBinding.inflate(getLayoutInflater());
            setContentView(userBinding.getRoot());
            TextView coins = userBinding.numCoins;
            coins.setText(String.valueOf(value));
        }
    }

    public void moveTo_dashboard_user(View view) {
        setContentView(R.layout.dashboard_user);
        userBinding = DashboardUserBinding.inflate(getLayoutInflater());
        setContentView(userBinding.getRoot());
        TextView coins = userBinding.numCoins;
        coins.setText(String.valueOf(value));
    }

    public void moveTo_dashboard_anonymous(View view) { setContentView(R.layout.dashboard_anonymous); }

    public void moveTo_notification_page(View view) {
        setContentView(R.layout.notification_page);
        if(findViewById(R.id.recyclerView_notifications) != null){
            RecyclerView recyclerView = findViewById(R.id.recyclerView_notifications);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            NotificationAdapter notificationAdapter = new NotificationAdapter(notificationList);
            recyclerView.setAdapter(notificationAdapter);
        }
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
        coins.setText(String.valueOf(value));

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
        coins.setText(String.valueOf(value));

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
        coins.setText(String.valueOf(value));

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
                imageView.setOnClickListener(v -> handleCardClick((ImageView) v, boardType));
            }
            else if (boardType == 1) // Board 3x4 user
            {
                ImageView imageView = (ImageView) binding3x4.MemoryGrid.getChildAt(i);
                imageView.setClickable(true);
                imageView.setTag(i);

                // Set click listener for each ImageView
                imageView.setOnClickListener(v -> handleCardClick((ImageView) v, boardType));
            }
            else if (boardType == 2) // Board 4x4 user
            {
                ImageView imageView = (ImageView) binding4x4.MemoryGrid.getChildAt(i);
                imageView.setClickable(true);
                imageView.setTag(i);

                // Set click listener for each ImageView
                imageView.setOnClickListener(v -> handleCardClick((ImageView) v, boardType));
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

    private void buyHint(View view, TextView coins, int boardType) {
        if(value <= 0) //   not enough coins
            notEnoughtCoins();
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
    private void gameOverPopUp(int boardType) {
        // Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set title, message, and buttons
        builder.setTitle("Game Finished!");
        builder.setMessage(String.format("Score: %d\n\n" + "Attempts: %d\n\n" + "Time: %02d:%02d", score, attempts, minutes, remainingSeconds));

        // Positive Button (e.g., OK)
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Handle OK button click
            dialog.dismiss(); // Close the pop-up
            if (boardType == 0) {
                moveTo_dashboard_anonymous(null);
            } else {
                moveTo_dashboard_user(null);
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}


package com.example.memorygame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.memorygame.databinding.Board3x4AnonymousBinding;


import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    // Variables
    private Board3x4AnonymousBinding binding;
    private ArrayList<MemoryCard> memoryCards = new ArrayList<>();
    MemoryCard FirstCard = null;
    private ImageView firstCardImageView = null; // Declare a variable to store the ImageView
    int matchCount = 0;
    boolean isWaiting = false;

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

    public void moveTo_board4x4(View view) {
        setContentView(R.layout.board4x4);
    }

    public void moveTo_board6x6(View view) {
        setContentView(R.layout.board6x6);
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

    public void moveTo_board3x4_anonymous(View view) {

        binding = Board3x4AnonymousBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupGame();
    }

    public void setupGame() {

        int cardCount = binding.MemoryGrid.getChildCount();
        memoryCards = CreateMemoryCards(cardCount);
        Collections.shuffle(memoryCards);
        initializeMemoryCards();

        FirstCard = null;
        firstCardImageView = null;
        matchCount = 0;
        isWaiting = false;

    }

    private void initializeMemoryCards() {
        for (int i = 0; i < memoryCards.size(); i++) {
            ImageView imageView = (ImageView) binding.MemoryGrid.getChildAt(i);
            imageView.setClickable(true);
            imageView.setTag(i);

            // Set click listener for each ImageView
            imageView.setOnClickListener(v -> handleCardClick((ImageView) v));
        }
    }

    private void handleCardClick(ImageView imageView) {
        if (isWaiting) {
        }else {
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

                        if (matchCount == memoryCards.size() / 2) {
                            // Game Over
                            Log.d("MemoryCard", "Game Over");
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
                    }
                }
            } else {
                Log.d("MemoryCard", "Card already flipped");
            }
        }
    }
}


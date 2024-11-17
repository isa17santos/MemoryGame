package com.example.memorygame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    public void moveTo_board3x4_anonymous(View view) {

        binding = Board3x4AnonymousBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        randomizeCards();
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

    public void randomizeCards() {
        int childCount = binding.GridLayout1.getChildCount();
        memoryCards = CreateMemoryCards(childCount);

        Collections.shuffle(memoryCards);

        binding.imageView1.setOnClickListener(view -> {
            // Handle the click event for imageView1 here
            memoryCards.get(0).onClick();
            binding.imageView1.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(0).getColor()));

            binding.imageView1.setImageResource(memoryCards.get(0).getImageId());

            Log.d("MemoryCard", "imageView1 clicked");
        });

        binding.imageView2.setOnClickListener(view -> {
            memoryCards.get(1).onClick();
            binding.imageView2.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(1).getColor()));

            binding.imageView2.setImageResource(memoryCards.get(1).getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView3.setOnClickListener(view -> {
            memoryCards.get(2).onClick();
            binding.imageView3.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(2).getColor()));

            binding.imageView3.setImageResource(memoryCards.get(2).getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView4.setOnClickListener(view -> {
            memoryCards.get(3).onClick();
            binding.imageView4.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(3).getColor()));

            binding.imageView4.setImageResource(memoryCards.get(3).getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView5.setOnClickListener(view -> {
            memoryCards.get(4).onClick();
            binding.imageView5.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(4).getColor()));

            binding.imageView5.setImageResource(memoryCards.get(4).getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView6.setOnClickListener(view -> {
            memoryCards.get(5).onClick();
            binding.imageView6.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(5).getColor()));

            binding.imageView6.setImageResource(memoryCards.get(5).getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView7.setOnClickListener(view -> {
            memoryCards.get(6).onClick();
            binding.imageView7.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(6).getColor()));

            binding.imageView7.setImageResource(memoryCards.get(6).getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView8.setOnClickListener(view -> {
            memoryCards.get(7).onClick();
            binding.imageView8.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(7).getColor()));

            binding.imageView8.setImageResource(memoryCards.get(7).getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView9.setOnClickListener(view -> {
            memoryCards.get(8).onClick();
            binding.imageView9.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(8).getColor()));

            binding.imageView9.setImageResource(memoryCards.get(8).getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView10.setOnClickListener(view -> {
            memoryCards.get(9).onClick();
            binding.imageView10.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(9).getColor()));

            binding.imageView10.setImageResource(memoryCards.get(9).getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView11.setOnClickListener(view -> {
            memoryCards.get(10).onClick();
            binding.imageView11.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(10).getColor()));

            binding.imageView11.setImageResource(memoryCards.get(10).getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView12.setOnClickListener(view -> {
            memoryCards.get(11).onClick();
            binding.imageView12.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards.get(11).getColor()));

            binding.imageView12.setImageResource(memoryCards.get(11).getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });
    }
}

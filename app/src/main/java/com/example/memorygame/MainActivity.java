package com.example.memorygame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.memorygame.databinding.ActivityMainBinding;
import com.example.memorygame.databinding.Board3x4AnonymousBinding;
import com.example.memorygame.databinding.Board6x6Binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Variables

    private final MemoryCard[] memoryCards = {new MemoryCard(R.drawable.candycane),
                                              new MemoryCard(R.drawable.christmas),
                                              new MemoryCard(R.drawable.christmastree),
                                              new MemoryCard(R.drawable.christmaswreath),
                                              new MemoryCard(R.drawable.christmasornament),
                                              new MemoryCard(R.drawable.december),
                                              new MemoryCard(R.drawable.hat),
                                              new MemoryCard(R.drawable.gingerbreadman),
                                              new MemoryCard(R.drawable.gift),
                                              new MemoryCard(R.drawable.giftbox),
                                              new MemoryCard(R.drawable.hotdrink),
                                              new MemoryCard(R.drawable.mistletoe)};
    private Board3x4AnonymousBinding binding;

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

    public ArrayList<Integer> imagesResources() {
        ArrayList<Integer> imageResources = new ArrayList<>();
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
        return imageResources;
    }

    public void randomizeCards() {
        // GridLayout gridLayout = findViewById(R.id.GridLayout1);  Commented because of crash conflict

        // Duplicate images and shuffle
        ArrayList<Integer> pairedImages = imagesResources();
        Collections.shuffle(pairedImages);

        //for (int i = 0; i < gridLayout.getChildCount(); i++) {    Commented because of conflict
            /*View child = gridLayout.getChildAt(i);    //  Commented because of conflict
            if (child instanceof CardView) {
                CardView cardView = (CardView) child;
                ImageView imageView2 = new ImageView(this);
                imageView2.setImageResource(pairedImages.get(i));
                cardView.addView(imageView2);
            }*/

        binding.imageView1.setOnClickListener(view -> {
            // Handle the click event for imageView1 here
            memoryCards[0].onClick();
            binding.imageView1.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[0].getColor()));

            binding.imageView1.setImageResource(memoryCards[0].getImageId());

            Log.d("MemoryCard", "imageView1 clicked");
        });

        binding.imageView2.setOnClickListener(view -> {
            // Handle the click event for imageView2 here
            memoryCards[1].onClick();
            binding.imageView2.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[1].getColor()));

            binding.imageView2.setImageResource(memoryCards[1].getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView3.setOnClickListener(view -> {
            // Handle the click event for imageView3 here
            memoryCards[2].onClick();
            binding.imageView3.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[2].getColor()));

            binding.imageView3.setImageResource(memoryCards[2].getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView4.setOnClickListener(view -> {
            // Handle the click event for imageView1 here
            memoryCards[3].onClick();
            binding.imageView4.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[3].getColor()));

            binding.imageView4.setImageResource(memoryCards[3].getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView5.setOnClickListener(view -> {
            // Handle the click event for imageView1 here
            memoryCards[4].onClick();
            binding.imageView5.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[4].getColor()));

            binding.imageView5.setImageResource(memoryCards[4].getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView6.setOnClickListener(view -> {
            // Handle the click event for imageView1 here
            memoryCards[5].onClick();
            binding.imageView6.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[5].getColor()));

            binding.imageView6.setImageResource(memoryCards[5].getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView7.setOnClickListener(view -> {
            // Handle the click event for imageView1 here
            memoryCards[6].onClick();
            binding.imageView7.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[6].getColor()));

            binding.imageView7.setImageResource(memoryCards[6].getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView8.setOnClickListener(view -> {
            // Handle the click event for imageView1 here
            memoryCards[7].onClick();
            binding.imageView8.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[7].getColor()));

            binding.imageView8.setImageResource(memoryCards[7].getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView9.setOnClickListener(view -> {
            // Handle the click event for imageView1 here
            memoryCards[8].onClick();
            binding.imageView9.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[8].getColor()));

            binding.imageView9.setImageResource(memoryCards[8].getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView10.setOnClickListener(view -> {
            // Handle the click event for imageView1 here
            memoryCards[9].onClick();
            binding.imageView10.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[9].getColor()));

            binding.imageView10.setImageResource(memoryCards[9].getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView11.setOnClickListener(view -> {
            // Handle the click event for imageView1 here
            memoryCards[10].onClick();
            binding.imageView11.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[10].getColor()));

            binding.imageView11.setImageResource(memoryCards[10].getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });

        binding.imageView12.setOnClickListener(view -> {
            // Handle the click event for imageView1 here
            memoryCards[11].onClick();
            binding.imageView12.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), memoryCards[11].getColor()));

            binding.imageView12.setImageResource(memoryCards[11].getImageId());

            Log.d("MemoryCard", "imageView2 clicked");
        });
    }
}

package com.example.memorygame;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
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
    
    public void moveTo_boardsize_page (View view)
    {
        setContentView(R.layout.boardsize_page);
    }

    public void moveTo_history_page (View view)
    {
        setContentView(R.layout.history_page);
    }

    public void moveTo_scoreboard_page (View view)
    {
        setContentView(R.layout.scoreboard_page);
    }

    public void moveTo_dashboard_user (View view)
    {
        setContentView(R.layout.dashboard_user);
    }

    public void moveTo_board3x4_anonymous (View view) { setContentView(R.layout.board3x4_anonymous);
    }

    public void moveTo_board3x4_user (View view)
    {
        setContentView(R.layout.board3x4_user);
    }

    public void moveTo_board4x4 (View view)
    {
        setContentView(R.layout.board4x4);
    }

    public void moveTo_board6x6 (View view)
    {
        setContentView(R.layout.board6x6);
    }

    public void moveTo_dashboard_anonymous (View view)
    {
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
    
    public void randomnizeCards(View view) {
        GridLayout gridLayout = findViewById(R.id.GridLayout1);
        ArrayList<Integer> pairedImages = imagesResources();
        Collections.shuffle(pairedImages);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof CardView) {
                CardView cardView = (CardView) child;
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(pairedImages.get(i));
                cardView.addView(imageView);
            }

        }
        
    }
}
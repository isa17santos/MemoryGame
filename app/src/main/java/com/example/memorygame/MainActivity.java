package com.example.memorygame;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

    public void moveTo_board3x4 (View view)
    {
        setContentView(R.layout.board3x4);
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
}
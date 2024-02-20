package com.example.juegos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent data = getIntent();
        name = data.getStringExtra("USERNAME");
        Button senku = findViewById(R.id.bt_senku);
        senku.setOnClickListener(v -> {
            Intent intent = new Intent(this, Senku.class);
            intent.putExtra("USERNAME", name);
            startActivity(intent);
        });
        Button board2048 = findViewById(R.id.bt_2048);
        board2048.setOnClickListener(v -> {
            Intent intent = new Intent(this, juego2048.class);
            intent.putExtra("USERNAME", name);
            startActivity(intent);
        });
        Button scores= findViewById(R.id.bt_scores);
        scores.setOnClickListener(v -> {
            Intent intent = new Intent(this, ScoreActivity.class);
            intent.putExtra("USERNAME", name);
            startActivity(intent);
        });


    }
}
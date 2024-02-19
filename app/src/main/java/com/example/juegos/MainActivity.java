package com.example.juegos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button senku = findViewById(R.id.bt_senku);
        senku.setOnClickListener(v -> {
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        });
        Button board2048 = findViewById(R.id.bt_2048);
        board2048.setOnClickListener(v -> {
            Intent intent = new Intent(this, juego2048.class);
            startActivity(intent);
        });

    }
}
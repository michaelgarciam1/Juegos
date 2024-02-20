package com.example.juegos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {
    private RecyclerView mRecycler2048;
    private RecyclerView mRecyclerSenku;
    private ScoresAdapter mAdapter2048;
    private ScoresAdapter mAdapterSenku;
    private ArrayList<Score> mData2048;
    private ArrayList<Score> mDataSenku;
    private Database db;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent data = getIntent();
        username = data.getStringExtra("USERNAME");
        setContentView(R.layout.activity_score);
        mRecycler2048 = findViewById(R.id.recyclerView2048);
        mRecyclerSenku = findViewById(R.id.recyclerViewSenku);
        db = new Database(this);
        mData2048 = new ArrayList<>();
        mDataSenku = new ArrayList<>();
        addDataSenku();
        addData2048();
        mRecycler2048.setLayoutManager(new GridLayoutManager(this, 1));

        mAdapter2048 = new ScoresAdapter(this, mData2048);
        mRecycler2048.setAdapter(mAdapter2048);

        mRecyclerSenku.setLayoutManager(new GridLayoutManager(this, 1));
        mAdapterSenku = new ScoresAdapter(this, mDataSenku);
        mRecyclerSenku.setAdapter(mAdapterSenku);

    }

    private void addData2048() {
        Cursor cursor = db.getScoreData("Score2048", username);
        if (cursor.getCount() == 0) {
            //Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                mData2048.add(new Score(cursor.getString(0), cursor.getString(1)));
            }
        }

    }

    private void addDataSenku() {
        Cursor cursor = db.getScoreData("ScoreSenku", username);
        if (cursor.getCount() == 0) {
            //Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Score sc = new Score(cursor.getString(0), cursor.getString(1));
                sc.changeScoreSenku();
                mDataSenku.add(sc);
            }
        }
    }
}
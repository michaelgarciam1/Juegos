package com.example.juegos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {
    private RecyclerView mRecycler2048;
    private RecyclerView mRecyclerSenku;
    private ScoresAdapter mAdapter;
    private ArrayList<Score> mData2048;
    private ArrayList<Score> mDataSenku;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        mRecycler2048 = findViewById(R.id.recyclerView2048);
        mRecyclerSenku = findViewById(R.id.recyclerViewSenku);
        mData2048 = new ArrayList<>();
        mDataSenku = new ArrayList<>();

        mRecycler2048.setLayoutManager(new GridLayoutManager(this, 1));

        mAdapter= new ScoresAdapter(this, mData2048);
        mRecycler2048.setAdapter(mAdapter);

        mRecyclerSenku.setLayoutManager(new GridLayoutManager(this, 1));
        mAdapter= new ScoresAdapter(this, mDataSenku);
        mRecyclerSenku.setAdapter(mAdapter);

    }

    private void addData(){
        mData2048.clear();
        mDataSenku.clear();
        db = new Database(this);
        Cursor c = db.getScoreData("Score2048");

        Cursor a = db.getScoreData("ScoreSenku");
        mAdapter.notifyDataSetChanged();
    }
}
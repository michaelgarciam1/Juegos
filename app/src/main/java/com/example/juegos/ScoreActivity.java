package com.example.juegos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private Button apply;
    private CheckBox allPlayers;
    private CheckBox order;

    private Button btClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("ActiveUser", "");

        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_score);
        mRecycler2048 = findViewById(R.id.recyclerView2048);
        mRecyclerSenku = findViewById(R.id.recyclerViewSenku);
        apply = findViewById(R.id.bt_apply);
        allPlayers = findViewById(R.id.checkPlayers);
        order = findViewById(R.id.checkOrder);
        btClear = findViewById(R.id.bt_cleardata);
        db = new Database(this);
        mData2048 = new ArrayList<>();
        mDataSenku = new ArrayList<>();
        addDataSenku(false, false);
        addData2048(false, false);
        mRecycler2048.setLayoutManager(new GridLayoutManager(this, 1));

        mAdapter2048 = new ScoresAdapter(this, mData2048);
        mRecycler2048.setAdapter(mAdapter2048);

        mRecyclerSenku.setLayoutManager(new GridLayoutManager(this, 1));
        mAdapterSenku = new ScoresAdapter(this, mDataSenku);
        mRecyclerSenku.setAdapter(mAdapterSenku);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback2048 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                db.deleteScoreData("Score2048", username, Integer.valueOf(mData2048.get(position).getScore()));
                mData2048.remove(position);
                mAdapter2048.notifyItemRemoved(position);
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback2048).attachToRecyclerView(mRecycler2048);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallbackSenku = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                String num = mDataSenku.get(position).getScore();
                String[] parts = num.split(":");
                int minuts = Integer.parseInt(parts[0]);
                int seconds = Integer.parseInt(parts[1]);
                int total = minuts * 60 + seconds;
                db.deleteScoreData("ScoreSenku", username, total);
                mDataSenku.remove(position);
                mAdapterSenku.notifyItemRemoved(position);
            }
        };
        new ItemTouchHelper(itemTouchHelperCallbackSenku).attachToRecyclerView(mRecyclerSenku);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean orden = order.isChecked();
                boolean todos = allPlayers.isChecked();
                addData2048(orden, todos);
                addDataSenku(orden, todos);
                mAdapter2048.notifyDataSetChanged();
                mAdapterSenku.notifyDataSetChanged();
            }
        });
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClearData();
            }
        });
        Button menu = findViewById(R.id.btbackScore);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void addData2048(boolean order, boolean allPlayers) {
        mData2048.clear();
        Cursor cursor;
        if (order) {
            cursor = db.orderData("Score2048");
        } else {
            cursor = db.getScoreData("Score2048");
        }
        if (cursor.getCount() == 0) {
            //Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                if (allPlayers) {
                    mData2048.add(new Score(cursor.getString(0), cursor.getString(1)));
                } else if (cursor.getString(0).equals(username)) {
                    mData2048.add(new Score(cursor.getString(0), cursor.getString(1)));

                }
            }

        }
    }

    private void addDataSenku(boolean order, boolean allPlayers) {
        mDataSenku.clear();
        Cursor cursor;
        if (order) {
            cursor = db.orderData("ScoreSenku");
        } else {
            cursor = db.getScoreData("ScoreSenku");
        }
        if (cursor.getCount() == 0) {
            //Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Score sc = new Score(cursor.getString(0), cursor.getString(1));
                sc.changeScoreSenku();
                if (allPlayers) {
                    mDataSenku.add(sc);
                } else if (cursor.getString(0).equals(username)) {
                    mDataSenku.add(sc);
                }
            }
        }
    }

    private void dialogClearData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all data");
        builder.setMessage("Do you want to delete all data?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            db.deleteScoreData("Score2048");
            db.deleteScoreData("ScoreSenku");
            mData2048.clear();
            mDataSenku.clear();
            mAdapter2048.notifyDataSetChanged();
            mAdapterSenku.notifyDataSetChanged();
        });
        builder.setNegativeButton("No", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

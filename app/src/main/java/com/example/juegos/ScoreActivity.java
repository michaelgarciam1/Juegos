package com.example.juegos;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

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
    private Button menu;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database(this);
        username = Util.getUserName(this);
        setContentView(R.layout.activity_score);

        initializeView();
        setListeners();

        setRecycler2048();
        setRecyclerSenku();

        touchHelper2048();
        touchHelperSenku();

        mediaPlayer = MediaPlayer.create(this, R.raw.fondo_menu);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
    private void initializeView() {
        mRecycler2048 = findViewById(R.id.recyclerView2048);
        mRecyclerSenku = findViewById(R.id.recyclerViewSenku);
        apply = findViewById(R.id.bt_apply);
        allPlayers = findViewById(R.id.checkPlayers);
        order = findViewById(R.id.checkOrder);
        btClear = findViewById(R.id.bt_cleardata);
        menu = findViewById(R.id.btbackScore);
    }

    private void setListeners() {
        btClear.setOnClickListener(view -> dialogClearData());

        menu.setOnClickListener(view -> {
            Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        apply.setOnClickListener(view -> {
            boolean orden = order.isChecked();
            boolean todos = allPlayers.isChecked();
            addData2048(orden, todos);
            addDataSenku(orden, todos);
            mAdapter2048.notifyDataSetChanged();
            mAdapterSenku.notifyDataSetChanged();
        });
    }

    private void setRecycler2048() {
        mData2048 = new ArrayList<>();
        addData2048(false, false);
        mRecycler2048.setLayoutManager(new GridLayoutManager(this, 1));
        mAdapter2048 = new ScoresAdapter(this, mData2048);
        mRecycler2048.setAdapter(mAdapter2048);
    }

    private void setRecyclerSenku() {
        mDataSenku = new ArrayList<>();
        addDataSenku(false, false);
        mRecyclerSenku.setLayoutManager(new GridLayoutManager(this, 1));
        mAdapterSenku = new ScoresAdapter(this, mDataSenku);
        mRecyclerSenku.setAdapter(mAdapterSenku);
    }

    private void touchHelper2048() {
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

    }

    private void touchHelperSenku() {
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

package com.example.juegos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class juego2048 extends AppCompatActivity {
    private GestureDetector mGestureDetector;
    private TextView tvScore;
    private TextView tvMaxScore;
    private TableLayout table;
    private board2048 board;

    private Button btUndo;
    private Button btNewGame;

    private Database db;
    private boolean isOver = false;

    private String nameUser;

    private TextView tvTime;
    private int tiempo;
    CountDownTimer timer;
    private Button btMenu;
    int prueba = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        nameUser = sharedPreferences.getString("ActiveUser", "");
        setContentView(R.layout.activity_board2048);
        table = findViewById(R.id.tableLayout);
        tvScore = findViewById(R.id.tv_score2048);
        tvScore.setText("0");
        tvMaxScore = findViewById(R.id.tvBestScore);
        tvTime=findViewById(R.id.tvTimer);

        mGestureDetector = new GestureDetector(this, new EscucharGestos());
        board = new board2048(4);
        db = new Database(this);
        repaintBoard();
        getMaxPuntuacion();
        btUndo = findViewById(R.id.bt_undo);
        btUndo.setOnClickListener(v -> {
            board.undoMove();
            repaintBoard();
            tvScore.setText(String.valueOf(board.getScore()));

        });
        btNewGame = findViewById(R.id.bt_reset);
        btNewGame.setOnClickListener(v -> {

           remakeGame();
        });
        btMenu = findViewById(R.id.bt_back2048);
        btMenu.setOnClickListener(v -> {
            Intent intent = new Intent(juego2048.this, MainActivity.class);
            startActivity(intent);
        });
        showTime();
    }
    private void showTime() {
        long tiempoInicio = System.currentTimeMillis();
        timer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            public void onTick(long millisUntilFinished) {
                long tiempoActual = System.currentTimeMillis() - tiempoInicio;
                tiempo = (int) tiempoActual / 1000;
                tvTime.setText(formatedTime(tiempo));
            }

            public void onFinish() {
                // Manejar finalización si es necesario
            }
        }.start();
    }



    private void repaintBoard() {
        for (int i = 0; i < 4; i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 4; j++) {
                TextView tv = (TextView) row.getChildAt(j);
                int valor = board.getBoard()[i][j];
                tv.setBackgroundColor(getResources().getColor(setColor(valor)));
                tv.setText(valor == 0 ? "" : String.valueOf(valor));
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class EscucharGestos extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2,
                               float velocityX, float velocityY) {
            float ancho = Math.abs(e2.getX() - e1.getX());
            float alto = Math.abs(e2.getY() - e1.getY());

            if (ancho > alto) {
                //derecha
                if (e2.getX() > e1.getX()) {
                    move(Direction.RIGHT);
                }//izquierda
                else {
                    move(Direction.LEFT);
                }
            }
            //arriba
            else {
                if (e1.getY() > e2.getY()) {
                    move(Direction.UP);
                }
                //abajo
                else {
                    move(Direction.DOWN);
                }
            }
            return true;
        }
    }

    private void move(Direction direction) {
        if (isOver) {
            return;
        }
        board.move(direction);
        repaintBoard();
        tvScore.setText(String.valueOf(board.getScore()));
        if (board.isFinished() == 1 || board.isFinished() == 0){
            setMaxPuntuacion();
            String message = board.isFinished() == 1 ? "You win!" : "Game over";
            alertGameOver(message);
            isOver = true;
        }
    }

    private void alertGameOver(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(message + "\n score: " + board.getScore());
        builder.setMessage("Do you want to play again?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
          remakeGame();
        });
        builder.setNegativeButton("No", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private int setColor(int value) {
        switch (value) {

            case 2:
                return R.color.num_2;
            case 4:
                return R.color.num_4;
            case 8:
                return R.color.num_8;
            case 16:
                return R.color.num_16;
            case 32:
                return R.color.num_32;
            case 64:
                return R.color.num_64;
            case 128:
                return R.color.num_128;
            case 256:
                return R.color.num_256;
            case 512:
                return R.color.num_512;
            case 1024:
                return R.color.num_1024;
            case 2048:
                return R.color.num_2048;
            default:
                return R.color.num_0;
        }
    }

    private void getMaxPuntuacion() {
        int max = db.getMaxScore2048(nameUser);
        tvMaxScore.setText(String.valueOf(max));
    }

    private void setMaxPuntuacion() {
        boolean saveScore = db.insertScoreData("Score2048", nameUser, board.getScore());
        if (!saveScore) {
            Toast.makeText(this, "Error saving score", Toast.LENGTH_SHORT);
        }
    }

    private void remakeGame(){
        if (timer != null) {
            timer.cancel();
        }
        tvTime.setText(formatedTime(tiempo));
        showTime();
        isOver = false;
        board = new board2048(4);
        repaintBoard();
        tvScore.setText(String.valueOf(board.getScore()));
    }
    public String formatedTime(int time) {
        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
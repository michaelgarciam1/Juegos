package com.example.juegos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Senku extends AppCompatActivity {
    GridLayout gridLayout;
    TextView tvTime;
    Button bUndo;
    Button reset;
    SenkuBoard board;

    TextView tvScore;
    private int tiempo;
    private int initialX = -1;
    private int initialY = -1;

    private int[] posSeleccionada = {-1, -1};
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        gridLayout = findViewById(R.id.gridLayout);
        bUndo = findViewById(R.id.idUndo);
        reset = findViewById(R.id.buttonReset);
        tvTime = findViewById(R.id.tvTime);
        tvScore = findViewById(R.id.tvBest);
        getMaxPuntuacion();

        board = new SenkuBoard();

        showTime();
        addImageViewsToGrid();
        repaintView();

        gridLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int column = getColumnFromTouch(event.getX(), gridLayout);
                    int row = getRowFromTouch(event.getY(), gridLayout);
                    touch(row, column);
                }
                return true;
            }
        });
        bUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Senku.this, "Undo", Toast.LENGTH_SHORT).show();
                board.undo();
                repaintView();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                }
                board = new SenkuBoard();
                repaintView();
                initialX = -1;
                initialY = -1;
                tiempo = 0;
                tvTime.setText(formatedTime(tiempo));
                showTime();
            }
        });
    }

    private void showTime() {
        long tiempoInicio = System.currentTimeMillis();
        timer=new CountDownTimer(Long.MAX_VALUE, 1000) {
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

    private void touch(int row, int column) {
//        Log.d("Senku", "Touch: " + row + " " + column);
        if (initialX == -1 && initialY == -1) {
            initialX = row;
            initialY = column;
            addSeleccionable(row, column);
//            Log.d("Senku", "Touch: " + initialX + " " + initialY);
        } else {
//            Log.d("Senku", "TouchInitial: " + initialX + " " + initialY);
//            Log.d("Senku", "Touch: " + row + " " + column);
            board.move(initialX, initialY, row, column);
            initialX = -1;
            initialY = -1;
            repaintView();

        }
        finishGame();
    }

    private int getColumnFromTouch(float x, GridLayout gridLayout) {
        Log.d("", "getColumnFromTouch: " + x);
        float columnWidth = gridLayout.getWidth() / gridLayout.getColumnCount();
        return (int) (x / columnWidth);
    }


    private int getRowFromTouch(float y, GridLayout gridLayout) {
        float rowHeight = gridLayout.getHeight() / gridLayout.getRowCount();
        return (int) (y / rowHeight);
    }

    private void finishGame() {
        switch (board.finishGame()) {
            case 0:
                //no se ha acabado
                break;
            case 1:
                // es una victoria
                Toast.makeText(this, "Has ganado", Toast.LENGTH_SHORT).show();
                setMaxPuntuacion();
                break;
            case 2:
                // es una derrota
                setMaxPuntuacion();
                Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void quitarAnterior() {
        if (posSeleccionada[0] != -1) {
            int index = posSeleccionada[0] * gridLayout.getColumnCount() + posSeleccionada[1];
            View view = gridLayout.getChildAt(index);

            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                imageView.setImageResource(R.drawable.circulo);
            }
        }
    }


    private void addSeleccionable(int row, int column) {

        int index = row * gridLayout.getColumnCount() + column;
        View view = gridLayout.getChildAt(index);

        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setImageResource(R.drawable.circuloamarillo);
        }
    }


    private void addImageViewsToGrid() {
        ImageView imageView;
        int index = 0;
        for (int i = 0; i < gridLayout.getRowCount(); i++) {
            for (int j = 0; j < gridLayout.getColumnCount(); j++) {
                imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.circulo);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setAdjustViewBounds(true);
                imageView.setId(index++);
                GridLayout.Spec rowSpec = GridLayout.spec(i);
                GridLayout.Spec colSpec = GridLayout.spec(j);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
                int widthInDp = (int) getResources().getDimension(R.dimen.image_width);
                int heightInDp = (int) getResources().getDimension(R.dimen.image_height);
                params.width = widthInDp;
                params.height = heightInDp;
                imageView.setLayoutParams(params);
                gridLayout.addView(imageView);
            }
        }
    }


//    public ImageView selectImage(){
//        ImageView imageView = (ImageView) view;
//        if (table[i][j] == 1) {
//            imageView.setImageResource(R.drawable.circulo);
//        } else if (table[i][j] == 0) {
//            imageView.setImageResource(R.drawable.circulovacio);
//        }
//        else {
//            imageView.setVisibility(View.GONE);
//        }
//    }

    public void repaintView() {
        int index = 0;

        for (int i = 0; i < gridLayout.getRowCount(); i++) {
            for (int j = 0; j < gridLayout.getColumnCount(); j++) {
                View view = gridLayout.getChildAt(index);
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
                    if (board.getPosition(i, j) == 1) {
                        imageView.setImageResource(R.drawable.circulo);
                    } else if (board.getPosition(i, j) == 0) {
                        imageView.setImageResource(R.drawable.circulovacio);
                    } else {
                        imageView.setVisibility(View.GONE);
                    }
                }
                index++;
            }
        }
    }

    private void getMaxPuntuacion() {

        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasJuego", Context.MODE_PRIVATE);
        int intValue = sharedPreferences.getInt("maxScoreSenku", 0);
        tvScore.setText("Best: " + formatedTime(intValue));
    }

    private void set0() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasJuego", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("maxScoreSenku", 0);
        editor.apply();
        tvScore.setText("Best: 00:00:00");
    }

    private void setMaxPuntuacion() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasJuego", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int maxScore = sharedPreferences.getInt("maxScoreSenku", 0);
        if (tiempo > maxScore) {
            editor.putInt("maxScoreSenku", (int) tiempo);
            editor.apply();
            tvScore.setText("Best: " + formatedTime((int) tiempo));
        }
    }

    private String formatedTime(int time) {
        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
package com.example.juegos;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Senku extends AppCompatActivity {
    GridLayout gridLayout;
    SenkuBoard board;

    private int[] posSeleccionada={-1,-1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        gridLayout = findViewById(R.id.gridLayout);
        board = new SenkuBoard();
        addImageViewsToGrid();
        tableToView();

        gridLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
//                seleccionarCasilla(event.getX(),event.getY());
                return true;
            }
        });
    }

    private int getColumnFromTouch(float x, GridLayout gridLayout) {
        float columnWidth = gridLayout.getWidth() / gridLayout.getColumnCount();
        return (int) (x / columnWidth);
    }


    private int getRowFromTouch(float y, GridLayout gridLayout) {
        float rowHeight = gridLayout.getHeight() / gridLayout.getRowCount();
        return (int) (y / rowHeight);
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

    private void seleccionarCasilla(float x, float y) {
        quitarAnterior();
        int column = getColumnFromTouch(x, gridLayout);
        int row = getRowFromTouch(y, gridLayout);
        int index = row * gridLayout.getColumnCount() + column;
        addSeleccionable(row,column);
        posSeleccionada[0]=row;
        posSeleccionada[1]=column;
    }

    private void addSeleccionable(int row ,int column){

        int index=row* gridLayout.getColumnCount() + column;
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

    public void tableToView(){
        int index = 0;

        for (int i = 0; i < gridLayout.getRowCount(); i++) {
            for (int j = 0; j < gridLayout.getColumnCount(); j++) {
                View view = gridLayout.getChildAt(index);
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
                    if (board.getPosition(i,j) == 1) {
                        imageView.setImageResource(R.drawable.circulo);
                    } else if (board.getPosition(i,j) == 0) {
                        imageView.setImageResource(R.drawable.circulovacio);
                    }
                    else {
                        imageView.setVisibility(View.GONE);
                    }
                }
                index++;
            }
        }
    }
}
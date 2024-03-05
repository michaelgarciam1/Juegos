package com.example.juegos;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    String name;


    MediaPlayer mediaPlayer;
    RecyclerView mRecyclerView;
    ImageView perfil;
    Database db;
    String nameUser;
    TextView nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this);
        nameUser = Util.getUserName(this);
        initializeView();
        initRecycler();
        cargarPerfil();
        mediaPlayer=MediaPlayer.create(this,R.raw.fondo_menu);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
    private void initializeView(){
        nombre = findViewById(R.id.textViewNombre);
        nombre.setText("Bienvenido: " + nameUser);
        mRecyclerView = findViewById(R.id.recyclerMenu);
    }

    private void initRecycler(){
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        MenuAdapter mAdapter = new MenuAdapter(this, ItemMenu.opciones(),this);
        mRecyclerView.setAdapter(mAdapter);
        perfil = findViewById(R.id.imgPerfil);
    }

    private void cargarPerfil() {
        try {
            Bitmap image = db.getPhoto(nameUser);
            if (image != null && image.getByteCount() > 0) {
                perfil.setImageBitmap(image);
            } else {
                perfil.setImageResource(R.drawable.perfil);
            }
        } catch (Exception e) {
            perfil.setImageResource(R.drawable.perfil);
            e.printStackTrace();
        }
    }

}
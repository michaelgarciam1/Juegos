package com.example.juegos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String name;
    RecyclerView mRecyclerView;
    ImageView perfil;
    Database db;
    String nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        nameUser = sharedPreferences.getString("ActiveUser", "");
        TextView nombre= findViewById(R.id.textViewNombre);
        nombre.setText("Bienvenido: "+ nameUser);
        mRecyclerView = findViewById(R.id.recyclerMenu);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        MenuAdapter mAdapter = new MenuAdapter(this, ItemMenu.opciones());
        mRecyclerView.setAdapter(mAdapter);
        perfil = findViewById(R.id.imgPerfil);
        cargarPerfil();
    }

    private void cargarPerfil() {
        try {
            Bitmap image = db.getPhoto(nameUser);
            perfil.setImageBitmap(image);
        }
        catch (Exception e){
            perfil.setImageResource(R.drawable.perfil);
            e.printStackTrace();
        }
    }
}
package com.example.juegos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class GameCenter extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    EditText user, password;
    Button login, register;

    private Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        db = new Database(this);
        initializeView();
        setListeners();
        mediaPlayer=MediaPlayer.create(this,R.raw.fondo_menu);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }



    private void initializeView() {
        user = findViewById(R.id.etName);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.bt_login);
        register = findViewById(R.id.bt_new_user);
    }

    private void setListeners(){
        login.setOnClickListener(v -> {

            String name = user.getText().toString();
            String pass = password.getText().toString();
            if (name.equals("") || pass.equals("")) {
                Util.alerta("Complete los campos", this);
            } else {
                Boolean checkuserpass = db.checkUser(name, pass);
                if (checkuserpass) {
                    //guardar el usuario en una shared preference como Usuario activo
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("ActiveUser", name);
                    editor.apply();
                    Intent intent = new Intent(GameCenter.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Util.alerta("Usuario o contraseña incorrectos", this);
                }
            }
        });

        register.setOnClickListener(v -> {
            String name = user.getText().toString();
            String pass = password.getText().toString();
            if (name.equals("") || pass.equals("")) {
                Util.alerta("Complete los campos", this);
            } else if (db.existUser(name)) {
                Util.alerta("Usuario ya registrado", this);
            } else {
                db.insertUserData(name, pass, null);
                Util.alerta("Nuevo usuario registrado", this);
            }
        });
    }

}
package com.example.juegos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class GameCenter extends AppCompatActivity {
    EditText user, password;
    Button login, register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        user = (EditText) findViewById(R.id.etName);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.bt_login);
        register = (Button) findViewById(R.id.bt_new_user);
        login.setOnClickListener(v -> {
            Database db = new Database(GameCenter.this);
            String name = user.getText().toString();
            String pass = password.getText().toString();
            if (name.equals("") || pass.equals("")) {
                Snackbar.make(v, "Complete los campos", Snackbar.LENGTH_SHORT).show();
            } else {
                Boolean checkuserpass = db.checkUser(name, pass);
                if (checkuserpass) {
                    //guardarlo en una shared preference

                    Intent intent = new Intent(GameCenter.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(v, "Credenciales incorrectas", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(v -> {
            Database db = new Database(GameCenter.this);
            String name = user.getText().toString();
            String pass = password.getText().toString();
            if (name.equals("") || pass.equals("")) {
                Snackbar.make(v, "Complete los campos", Snackbar.LENGTH_SHORT).show();
            } else if (db.existUser(name)) {
                Snackbar.make(v, "Usuario ya existe", Snackbar.LENGTH_SHORT).show();
            } else {
                db.insertUserData(name, pass);
                Snackbar.make(v, "Usuario registrado", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
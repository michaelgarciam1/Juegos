package com.example.juegos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
               alerta("Complete los campos");
            } else {
                Boolean checkuserpass = db.checkUser(name, pass);
                if (checkuserpass) {
                    //guardarlo en una shared preference
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("ActiveUser", name);
                    editor.apply();
                    Intent intent = new Intent(GameCenter.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    alerta("Usuario o contraseña incorrectos");
                }
            }
        });

        register.setOnClickListener(v -> {
            Database db = new Database(GameCenter.this);
            String name = user.getText().toString();
            String pass = password.getText().toString();
            if (name.equals("") || pass.equals("")) {
                alerta("Complete los campos");
            } else if (db.existUser(name)) {
                alerta("Usuario ya registrado");
            } else {
                db.insertUserData(name, pass,null);
                alerta("Nuevo usuario registrado");
            }
        });
    }
    private void alerta(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Vale",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
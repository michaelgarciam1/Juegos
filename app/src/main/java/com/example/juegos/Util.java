package com.example.juegos;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import java.util.Random;

public class Util {

    public static void alerta(String mensaje, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Vale", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static String formatedTime(int time) {
        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void createData(Context context, String username) {
        Database db = new Database(context);
        String[] names = new String[]{"Michael", "Luis", "Juan", "Pedro", "Maria", "Sofia", "Ana", "Carlos", "Jose", "Andres", username};

        Random ran = new Random();
        for (int i = 0; i < 40; i++) {
            String randomName = names[ran.nextInt(names.length)]; // Obtener un nombre aleatorio de la matriz
            db.insertUserData(randomName, "123", null);
            db.insertScoreData("Score2048", randomName, ran.nextInt(1000));
            db.insertScoreData("ScoreSenku", randomName, ran.nextInt(1000));
        }
    }

    public static String getUserName(Context context) {
        return context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
                .getString("ActiveUser", "");
    }


}


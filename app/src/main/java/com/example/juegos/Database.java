package com.example.juegos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "Score.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Score2048(name TEXT, score INT)");
        DB.execSQL("create Table ScoreSenku(name TEXT, score INT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Score2048");
        DB.execSQL("drop Table if exists ScoreSenku");
    }

    public Boolean insertScoreData(String tabla, String name, int score) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("score", score);
        long result = DB.insert(tabla, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getScoreData(String tabla) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from "+tabla, null);
        return cursor;
    }
}
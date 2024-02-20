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
        DB.execSQL("create Table Users(name TEXT, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Score2048");
        DB.execSQL("drop Table if exists ScoreSenku");
        DB.execSQL("drop Table if exists Users");
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

    public void insertUserData(String name, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        DB.insert("Users", null, contentValues);
    }

    public boolean checkUser(String name, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Users where name = ? and password = ?", new String[]{name, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean existUser(String name) {
           SQLiteDatabase DB = this.getWritableDatabase();
            Cursor cursor = DB.rawQuery("Select * from Users where name = ?", new String[]{name});
            if (cursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        }
    public void deleteScoreData(String tabla) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("delete from "+tabla);
    }

    public Cursor getScoreData(String tabla, String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from "+tabla+" WHERE name=?", new String[]{name});
        return cursor;
    }


    //en el senku el mejor es el que menos tarda
    public int getMaxScoreSenku(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select MIN(score) from ScoreSenku WHERE name=?", new String[]{name});
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
    public int getMaxScore2048(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT MAX(score) FROM Score2048 WHERE name=?", new String[]{name});
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

}
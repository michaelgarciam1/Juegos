package com.example.juegos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;


public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "Score.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL("create Table Score2048(name TEXT, score INT)");
        DB.execSQL("create Table ScoreSenku(name TEXT, score INT)");
        DB.execSQL("create Table Users(name TEXT, password TEXT,photo BLOB)");
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

    public void insertUserData(String name, String password, byte[] photo) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("photo", photo);
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
    public void setPhoto(String name, Bitmap userPhoto){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userPhoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] photo = baos.toByteArray();
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("photo", photo);
        // Update the photo of the user
        DB.update("Users", contentValues, "name = ?", new String[]{name});
    }
    public Bitmap getPhoto(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT photo FROM Users WHERE name = ?", new String[]{name});

        Bitmap photoBitmap = null;

        if (cursor != null && cursor.moveToFirst()) {
            byte[] photo = cursor.getBlob(0);

            // Convert byte array to Bitmap without using DbBitmapUtility
            if (photo != null && photo.length > 0) {
                photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            }

            cursor.close();
        }

        return photoBitmap;
    }
    public boolean existUser(String name) {
           SQLiteDatabase DB = this.getWritableDatabase();
            Cursor cursor = DB.rawQuery("Select * from Users where name = ?", new String[]{name});
            System.out.println(cursor.getCount());
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
    public void deleteScoreData(String tabla, String name, int score) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("DELETE FROM " + tabla + " WHERE name = ? AND score = ?", new String[]{name, String.valueOf(score)});
    }
    public void deleteData(String table){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("delete from "+table);
    }

    public Cursor getScoreData(String tabla) {
        SQLiteDatabase DB = this.getWritableDatabase();
        //ordenados por score
        Cursor cursor = DB.rawQuery("Select * from "+tabla , null);
        return cursor;
    }

    public Cursor orderData(String tabla) {
        SQLiteDatabase DB = this.getWritableDatabase();
        //ordenados por score
        Cursor cursor = DB.rawQuery("Select * from "+tabla+" ORDER BY score", null);
        return cursor;
    }

    public void updatePassword(String userName, String newPassword) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", newPassword);
        DB.update("Users", contentValues, "name = ?", new String[]{userName});
    }


}
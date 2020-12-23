package com.coursework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/***
 * Класс отвечающий за конфигурацию БД
 */
public class dbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "violationsDB";
    public static final String TABLE_photos= "photos";
    public static final String TABLE_violations= "violations";
    public static final String KEY_ID = "_id";
    public static final String KEY_NUMBERAUTO = "numberAuto";
    public static final String KEY_VIOLATIONDATE = "Date";
    public static final String KEY_VIOLATIONGPS = "Gps";
    public static  final String KEY_PHOTO="Photo";
    public static  final String ID_VIOLATION="idViolation";

    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_violations + "(" + KEY_ID + " integer primary key AUTOINCREMENT NOT NULL,  "+ KEY_NUMBERAUTO
                + " text," + KEY_VIOLATIONDATE + " date," + KEY_VIOLATIONGPS + " text" + ")" );
        db.execSQL("create table "+ TABLE_photos + "(" + KEY_ID + " integer primary key AUTOINCREMENT NOT NULL, "+ID_VIOLATION+" REFERENCES "+TABLE_violations+"(_id),"
                 + KEY_PHOTO + " blob" + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

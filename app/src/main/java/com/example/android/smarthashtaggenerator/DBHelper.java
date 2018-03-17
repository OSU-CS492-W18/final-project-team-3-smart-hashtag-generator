package com.example.android.smarthashtaggenerator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Owner on 3/16/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hashtag.db";
    private static int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SAVED_REPOS_TABLE =
                "CREATE TABLE " + DBContract.SavedResults.TABLE_NAME + "(" +
                        DBContract.SavedResults._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DBContract.SavedResults.COLUMN_PHOTO + " TEXT NOT NULL, " +
                        DBContract.SavedResults.COLUMN_TAGS+ " TEXT NOT NULL, " +
                        DBContract.SavedResults.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ");";
        db.execSQL(SQL_CREATE_SAVED_REPOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.SavedResults.TABLE_NAME + ";");
        onCreate(db);
    }

}

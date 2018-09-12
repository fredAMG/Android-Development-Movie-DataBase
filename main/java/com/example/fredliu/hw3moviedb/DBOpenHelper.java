package com.example.fredliu.hw3moviedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fredliu on 10/13/17.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 4;
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_RELEASE_DATE = "release";
    public static final String COLUMN_NAME_VOTE = "vote";
    public static final String COLUMN_NAME_POPULARITY = "popularity";
    public static final String COLUMN_NAME_OVERVIEW = "overview";
    public static final String COLUMN_NAME_POSTER_PATH = "poster";



    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_RELEASE_DATE + " TEXT," +
                    COLUMN_NAME_VOTE + " TEXT," +
                    COLUMN_NAME_POPULARITY + " TEXT," +
                    COLUMN_NAME_OVERVIEW + " TEXT," +
                    COLUMN_NAME_POSTER_PATH + " TEXT)";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

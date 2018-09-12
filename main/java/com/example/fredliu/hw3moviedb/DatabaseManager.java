package com.example.fredliu.hw3moviedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fredliu on 10/13/17.
 */

public class DatabaseManager {

    private SQLiteOpenHelper dbOpenHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public void open() {
        database = dbOpenHelper.getWritableDatabase();
    }

    public void close() {
        dbOpenHelper.close();
    }

    public void insertMovieInfo(String title, String release, String vote, String popularity, String overview, String poster) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_NAME_TITLE, title);
        values.put(DBOpenHelper.COLUMN_NAME_RELEASE_DATE, release);
        values.put(DBOpenHelper.COLUMN_NAME_VOTE, vote);
        values.put(DBOpenHelper.COLUMN_NAME_POPULARITY, popularity);
        values.put(DBOpenHelper.COLUMN_NAME_OVERVIEW, overview);
        values.put(DBOpenHelper.COLUMN_NAME_POSTER_PATH, poster);




        database.insert(DBOpenHelper.TABLE_NAME, null, values);
    }

    public List<Movie> getAllRecordsOrderedBy(String key) {
        Cursor cursor = database.query(DBOpenHelper.TABLE_NAME,
                new String[]{
                        DBOpenHelper.COLUMN_ID,
                        DBOpenHelper.COLUMN_NAME_TITLE,
                        DBOpenHelper.COLUMN_NAME_RELEASE_DATE,
                        DBOpenHelper.COLUMN_NAME_VOTE,
                        DBOpenHelper.COLUMN_NAME_POPULARITY,
                        DBOpenHelper.COLUMN_NAME_OVERVIEW,
                        DBOpenHelper.COLUMN_NAME_POSTER_PATH
                }, null, null, null, null, key + " DESC");
        cursor.moveToFirst();
        Movie movie;
        List<Movie> result =new ArrayList<Movie>();
        while (!cursor.isAfterLast()) {
            movie = new Movie();
            movie.setTitle(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_TITLE)));
            movie.setDate(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_RELEASE_DATE)));
            movie.setRating(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_VOTE)));
            movie.setPopularity(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_POPULARITY)));
            movie.setOverview(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_OVERVIEW)));
            movie.setPoster(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_POSTER_PATH)));
            cursor.moveToNext();
            result.add(movie);
        }
        return result;

        //return the list ordered by the key
        //The key should be one of the column keys defined in DBOpenHelper.
    }

    public List<Movie> getAllRecords() {
        Cursor cursor = database.query(DBOpenHelper.TABLE_NAME,
                new String[]{
                        DBOpenHelper.COLUMN_ID,
                        DBOpenHelper.COLUMN_NAME_TITLE,
                        DBOpenHelper.COLUMN_NAME_RELEASE_DATE,
                        DBOpenHelper.COLUMN_NAME_VOTE,
                        DBOpenHelper.COLUMN_NAME_POPULARITY,
                        DBOpenHelper.COLUMN_NAME_OVERVIEW,
                        DBOpenHelper.COLUMN_NAME_POSTER_PATH
                }, null, null, null, null, null, null);
        cursor.moveToFirst();
        Movie movie;
        List<Movie> result =new ArrayList<Movie>();
        while (!cursor.isAfterLast()) {
            movie = new Movie();
            movie.setTitle(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_TITLE)));
            movie.setDate(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_RELEASE_DATE)));
            movie.setRating(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_VOTE)));
            movie.setPopularity(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_POPULARITY)));
            movie.setOverview(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_OVERVIEW)));
            movie.setPoster(
                    cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_POSTER_PATH)));
            cursor.moveToNext();
            result.add(movie);
        }
        return result;
    }

    public void deleteAll() {
        if (database.isOpen()) {
            database.execSQL("DELETE FROM " + DBOpenHelper.TABLE_NAME);
        }
    }
}

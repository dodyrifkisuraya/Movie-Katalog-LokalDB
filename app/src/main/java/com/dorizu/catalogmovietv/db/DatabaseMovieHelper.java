package com.dorizu.catalogmovietv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseMovieHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbfavorite";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FILM = String.format("CREATE TABLE %s "+
            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT UNIQUE," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            DatabaseMovieContract.TABLE_NAME,
            DatabaseMovieContract.FilmColumns._ID,
            DatabaseMovieContract.FilmColumns.TITLE,
            DatabaseMovieContract.FilmColumns.DESCRIPTION,
            DatabaseMovieContract.FilmColumns.POSTER_PATH);

    public DatabaseMovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FILM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseMovieContract.TABLE_NAME);
        onCreate(db);
    }
}

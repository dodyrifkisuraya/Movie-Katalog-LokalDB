package com.dorizu.catalogmovietv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseTvHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbTvfavorite";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FILM = String.format("CREATE TABLE %s "+
            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT UNIQUE," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            DatabaseTvShowContract.TABLE_NAME,
            DatabaseTvShowContract.TvColumns._ID,
            DatabaseTvShowContract.TvColumns.TITLE,
            DatabaseTvShowContract.TvColumns.DESCRIPTION,
            DatabaseTvShowContract.TvColumns.POSTER_PATH);

    public DatabaseTvHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FILM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseTvShowContract.TABLE_NAME);
        onCreate(db);
    }
}

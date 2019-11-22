package com.dorizu.catalogmovietv.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dorizu.catalogmovietv.model.FavoritItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dorizu.catalogmovietv.db.DatabaseMovieContract.TABLE_NAME;
import static com.dorizu.catalogmovietv.db.DatabaseMovieContract.FilmColumns.DESCRIPTION;
import static com.dorizu.catalogmovietv.db.DatabaseMovieContract.FilmColumns.POSTER_PATH;
import static com.dorizu.catalogmovietv.db.DatabaseMovieContract.FilmColumns.TITLE;

public class MovieHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseMovieHelper databaseMovieHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        databaseMovieHelper = new DatabaseMovieHelper(context);
    }

    public static MovieHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                INSTANCE = new MovieHelper(context);
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseMovieHelper.getWritableDatabase();
    }

    public void close(){
        databaseMovieHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll(){
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC"
        );
    }

    public Cursor queryById(String id){
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }

    public ArrayList<FavoritItem> getAllFilm(){
        ArrayList<FavoritItem> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + "ASC",
                null);
        cursor.moveToFirst();
        FavoritItem modelFavorite;

        if (cursor.getCount() > 0){
            do{
                modelFavorite = new FavoritItem();
                modelFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                modelFavorite.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                modelFavorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                modelFavorite.setThumnail(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFilm(FavoritItem modelFavorite){
        ContentValues args = new ContentValues();
        args.put(TITLE, modelFavorite.getJudul());
        args.put(DESCRIPTION, modelFavorite.getOverview());
        args.put(POSTER_PATH, modelFavorite.getThumnail());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteFilm(int id){
        return database.delete(TABLE_NAME, _ID + "='" + id + "'", null);
    }
}

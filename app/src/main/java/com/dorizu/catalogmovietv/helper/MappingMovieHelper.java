package com.dorizu.catalogmovietv.helper;

import android.database.Cursor;

import com.dorizu.catalogmovietv.db.DatabaseMovieContract;
import com.dorizu.catalogmovietv.model.FavoritItem;

import java.util.ArrayList;

public class MappingMovieHelper {

    public static ArrayList<FavoritItem> mapCursorToArrayList(Cursor moviesCursor){
        ArrayList<FavoritItem> moviesList = new ArrayList<>();

        while (moviesCursor.moveToNext()){
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseMovieContract.FilmColumns._ID));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseMovieContract.FilmColumns.TITLE));
            String description = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseMovieContract.FilmColumns.DESCRIPTION));
            String imgPoster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseMovieContract.FilmColumns.POSTER_PATH));
            moviesList.add(new FavoritItem(title, description, imgPoster, id));
        }

        return moviesList;
    }
}

package com.dorizu.catalogmovietv.helper;

import android.database.Cursor;

import com.dorizu.catalogmovietv.model.FavoritItem;
import com.dorizu.catalogmovietv.db.DatabaseTvShowContract;

import java.util.ArrayList;

public class MappingTvHelper {

    public static ArrayList<FavoritItem> mapCursorToArrayList(Cursor moviesCursor){
        ArrayList<FavoritItem> moviesList = new ArrayList<>();

        while (moviesCursor.moveToNext()){
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseTvShowContract.TvColumns._ID));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseTvShowContract.TvColumns.TITLE));
            String description = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseTvShowContract.TvColumns.DESCRIPTION));
            String imgPoster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseTvShowContract.TvColumns.POSTER_PATH));
            moviesList.add(new FavoritItem(title, description, imgPoster, id));
        }

        return moviesList;
    }
}

package com.dorizu.catalogmovietv.db;

import android.provider.BaseColumns;

public class DatabaseMovieContract {

    static String TABLE_NAME = "film";

    public static final class FilmColumns implements BaseColumns {

        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String POSTER_PATH = "poster_path";
    }
}

package com.dorizu.catalogmovietv.db;

import android.provider.BaseColumns;

public class DatabaseTvShowContract {

    static String TABLE_NAME = "tvshow";

    public static final class TvColumns implements BaseColumns {

        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String POSTER_PATH = "poster_path";
    }
}

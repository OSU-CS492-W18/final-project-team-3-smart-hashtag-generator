package com.example.android.smarthashtaggenerator;
import android.provider.BaseColumns;

/**
 * Created by Owner on 3/16/2018.
 */

public class DBContract {

    /**
     * Created by Owner on 3/3/2018.
     */
    private  DBContract() {}

    public static class SavedResults implements BaseColumns {
        public static final String TABLE_NAME = "savedResults";
        public static final String COLUMN_PHOTO = "Photo";
        public static final String COLUMN_TAGS = "Tags";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}


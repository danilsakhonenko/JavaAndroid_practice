package com.example.lab4.db;

import android.provider.BaseColumns;

public class DBConstants {

    private DBConstants() {}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "museums";
        public static final String COLUMN_MUSEUM = "museum";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EXHIBITS = "exhibits";
        public static final String COLUMN_PRIVATE = "private";
    }

    public static final int DB_VERSION=1;
    public static final String DB_NAME="museums_db.db";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_MUSEUM + " TEXT," +
                    FeedEntry.COLUMN_CITY + " TEXT," +
                    FeedEntry.COLUMN_YEAR + " INTEGER," +
                    FeedEntry.COLUMN_PHONE + " TEXT," +
                    FeedEntry.COLUMN_EXHIBITS + " INTEGER," +
                    FeedEntry.COLUMN_PRIVATE + " NUMERIC)";

    public static final String SQL_DELETE_RECORD="DELETE FROM " +
            FeedEntry.TABLE_NAME + " WHERE _id = ";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}


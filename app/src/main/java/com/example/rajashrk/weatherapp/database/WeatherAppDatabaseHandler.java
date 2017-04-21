package com.example.rajashrk.weatherapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class WeatherAppDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WeatherApp.db";
    private static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_FAVOURITES_TABLE =
            "CREATE TABLE " + DatabaseTableNames.TABLE_NAME_FAVOURITE + " (" +
                    DatabaseTableNames.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    DatabaseTableNames.COLUMN_NAME_CITY + " TEXT NOT NULL," +
                    DatabaseTableNames.COLUMN_NAME_LATITUDE + " REAL NOT NULL," +
                    DatabaseTableNames.COLUMN_NAME_LONGITUDE + " REAL NOT NULL"
                    + ")";

    private static WeatherAppDatabaseHandler instance;

    static synchronized WeatherAppDatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new WeatherAppDatabaseHandler(context);
        }
        return instance;
    }


    private WeatherAppDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseTableNames.TABLE_NAME_FAVOURITE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}

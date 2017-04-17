package com.example.rajashrk.weatherapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import static com.example.rajashrk.weatherapp.database.DatabaseTableNames.COLUMN_NAME_CITY;
import static com.example.rajashrk.weatherapp.database.DatabaseTableNames.TABLE_NAME_FAVOURITE;

public class FavouritesRepository {
    private static final String TAG = FavouritesRepository.class.getSimpleName();

    private final WeatherAppDatabaseHandler weatherAppDatabaseHandler;

    public FavouritesRepository(Context context) {
        weatherAppDatabaseHandler = WeatherAppDatabaseHandler.getInstance(context);
    }

    public boolean saveFavourite(String name) {
        try (SQLiteDatabase sqLiteDatabase = weatherAppDatabaseHandler.getWritableDatabase()) {
            ContentValues contentValue = new ContentValues();
            contentValue.put(COLUMN_NAME_CITY, name);
            long insert = sqLiteDatabase.insert(TABLE_NAME_FAVOURITE, null, contentValue);
            return insert > 0;
        } catch (SQLiteException exception) {
            Log.e(TAG, exception.getLocalizedMessage());
        }
        return  false;
    }

    public boolean favouriteAlreadyExists(String cityName) {
        try (SQLiteDatabase sqLiteDatabase = weatherAppDatabaseHandler.getReadableDatabase()) {
            String whereClause = COLUMN_NAME_CITY + " = ?";
            String[] whereArgs = new String[] {
                    cityName
            };
            try (Cursor query = sqLiteDatabase.query(TABLE_NAME_FAVOURITE, new String[]{COLUMN_NAME_CITY}, whereClause, whereArgs, null, null, null)) {
                return query.getCount() > 1;
            }
        } catch (SQLiteException exception) {
            Log.e(TAG, exception.getLocalizedMessage());
        }
        return  false;
    }
}

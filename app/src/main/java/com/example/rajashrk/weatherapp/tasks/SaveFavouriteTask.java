package com.example.rajashrk.weatherapp.tasks;


import android.os.AsyncTask;
import android.view.View;

import com.example.rajashrk.weatherapp.SaveFavouriteListener;
import com.example.rajashrk.weatherapp.database.FavouritesRepository;
import com.example.rajashrk.weatherapp.model.Weather;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SaveFavouriteTask extends AsyncTask<Void, Void, Boolean>{
    private final Weather weather;
    private final FavouritesRepository favouritesRepository;
    private final SaveFavouriteListener saveFavouriteListener;

    public SaveFavouriteTask(FavouritesRepository favouritesRepository, SaveFavouriteListener saveFavouriteListener, Weather currentWeather) {
        this.favouritesRepository = favouritesRepository;
        this.saveFavouriteListener = saveFavouriteListener;
        this.weather = currentWeather;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String cityName = weather.getName();
        double latitude = weather.getCoord().getLat();
        double longitude = weather.getCoord().getLon();
        return favouritesRepository.saveFavourite(cityName, latitude, longitude);
    }

    @Override
    protected void onPostExecute(Boolean favouriteSaved) {
        super.onPostExecute(favouriteSaved);
        if(favouriteSaved) {
            saveFavouriteListener.displayToast("Favourite saved successfully");
            saveFavouriteListener.showSaveAsFavouriteButton(GONE);
        } else {
            saveFavouriteListener.displayToast("An error occurred while saving city as favourite");
            saveFavouriteListener.showSaveAsFavouriteButton(VISIBLE);
        }

    }
}

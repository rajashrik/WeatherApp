package com.example.rajashrk.weatherapp.tasks;


import android.os.AsyncTask;

import com.example.rajashrk.weatherapp.SaveFavouriteListener;
import com.example.rajashrk.weatherapp.database.FavouritesRepository;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CheckFavouriteAlreadyExistTask extends AsyncTask<String, Void, Boolean>{
    private FavouritesRepository favouritesRepository;
    private SaveFavouriteListener saveFavouriteListener;

    public CheckFavouriteAlreadyExistTask(FavouritesRepository favouritesRepository, SaveFavouriteListener saveFavouriteListener) {
        this.favouritesRepository = favouritesRepository;
        this.saveFavouriteListener = saveFavouriteListener;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String cityName = params[0];
        return favouritesRepository.favouriteAlreadyExists(cityName);
    }

    @Override
    protected void onPostExecute(Boolean favouriteAlreadyExists) {
        super.onPostExecute(favouriteAlreadyExists);
        if(favouriteAlreadyExists) {
            saveFavouriteListener.showSaveAsFavouriteButton(GONE);
        } else {
            saveFavouriteListener.showSaveAsFavouriteButton(VISIBLE);
        }

    }
}

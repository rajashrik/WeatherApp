package com.example.rajashrk.weatherapp.tasks;


import android.os.AsyncTask;

import com.example.rajashrk.weatherapp.SaveFavouriteListener;
import com.example.rajashrk.weatherapp.database.FavouritesRepository;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SaveFavouriteTask extends AsyncTask<String, Void, Boolean>{
    private FavouritesRepository favouritesRepository;
    private SaveFavouriteListener saveFavouriteListener;

    public SaveFavouriteTask(FavouritesRepository favouritesRepository, SaveFavouriteListener saveFavouriteListener) {
        this.favouritesRepository = favouritesRepository;
        this.saveFavouriteListener = saveFavouriteListener;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String cityName = params[0];
        return favouritesRepository.saveFavourite(cityName);
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

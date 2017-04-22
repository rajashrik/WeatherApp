package com.example.rajashrk.weatherapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajashrk.weatherapp.database.FavouritesRepository;
import com.example.rajashrk.weatherapp.location.LocationTracker;
import com.example.rajashrk.weatherapp.model.Weather;
import com.example.rajashrk.weatherapp.model.WeatherForecastResponse;
import com.example.rajashrk.weatherapp.presenter.WeatherPresenter;
import com.example.rajashrk.weatherapp.tasks.AsyncCurrentWeatherTask;
import com.example.rajashrk.weatherapp.tasks.AsyncWeatherForecastTask;
import com.example.rajashrk.weatherapp.tasks.CheckFavouriteAlreadyExistTask;
import com.example.rajashrk.weatherapp.tasks.SaveFavouriteTask;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainActivity extends AppCompatActivity implements WeatherResponseListener, SaveFavouriteListener {

    private static final int SEARCH_CODE = 9999;
    private static final int FAVOURITES_CODE = 9998;
    private Weather currentWeather = null;
    private LocationTracker locationTracker;
    private static final double defaultLat = 17.3850f;
    private static final double defaultLong = 78.4867f;
    private static double lat = defaultLat;
    private static double lon = defaultLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showWeatherOfCurrentCity();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View searchImageView = findViewById(R.id.searchImageView);
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSearchActivity();
            }
        });

        View favouritesImageView = findViewById(R.id.favouritesImageView);
        favouritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchFavouritesActivity();
            }
        });
        locationTracker = new LocationTracker(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(lat == defaultLat && lon == defaultLong) {
            locationTracker.refreshLocation();
        }else {
            showWeatherOfCurrentCity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 200 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            locationTracker.refreshLocation();
    }

    public void fetchForecast(View view) {
        String weatherUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + currentWeather.getName() + "&units=metric" + "&" + "APPID=" + "ebbc66b823072502c81339f5b0b9b042";
        AsyncWeatherForecastTask task = new AsyncWeatherForecastTask(this);
        task.execute(weatherUrl);
    }

    @Override
    public void weatherForecastReceived(String data) {
        Gson gson = new Gson();
        WeatherForecastResponse weatherList = gson.fromJson(data, WeatherForecastResponse.class);
        showWeatherForecast(weatherList);
    }

    @Override
    public void weatherForecastFailed() {
        displayToast("Failed to fetch weather forecast");
    }

    public void saveAsFavourite(View view) {
        FavouritesRepository favouritesRepository = new FavouritesRepository(this);
        new SaveFavouriteTask(favouritesRepository, this, currentWeather).execute();
    }

    private void showWeatherOfCurrentCity() {
        fetchWeatherForLocation(lat, lon);
    }

    public void renderWeatherList(Weather weather) {
        this.currentWeather = weather;
        renderWeatherList();
    }

    private void renderWeatherList() {
        showFavouriteButtonIfNotSavedAsFavourite();

        WeatherPresenter presenter = new WeatherPresenter(currentWeather);
        TextView cityName = (TextView) findViewById(R.id.cityName);
        cityName.setText(currentWeather.getName());

        TextView date = (TextView) findViewById(R.id.date);
        date.setText(presenter.getDayOfTheWeek());

        TextView temperature = (TextView) findViewById(R.id.temperature);
        temperature.setText(presenter.getTemperatureInCelsius());

        TextView weatherDescription = (TextView) findViewById(R.id.weatherDescription);
        weatherDescription.setText(currentWeather.getWeather().get(0).getDescription());

        ImageView weatherImage = (ImageView) findViewById(R.id.weatherImage);
        WeatherDescriptionToIconTranslator translator = new WeatherDescriptionToIconTranslator();
        weatherImage.setImageResource(translator.getDetailImageResourceId(currentWeather.getWeather().get(0).getDescription()));
    }

    private void showFavouriteButtonIfNotSavedAsFavourite() {
        FavouritesRepository favouritesRepository = new FavouritesRepository(this);
        new CheckFavouriteAlreadyExistTask(favouritesRepository, this).execute(currentWeather.getName());
    }

    private void showWeatherForecast(WeatherForecastResponse weatherList) {
        Intent intent = new Intent(this, DetailWeatherActivity.class);
        Gson gson = new Gson();
        String weatherData = gson.toJson(weatherList, WeatherForecastResponse.class);
        intent.putExtra("weatherData", weatherData);
        startActivity(intent);
    }

    @Override
    public void showSaveAsFavouriteButton(int visibility) {
        TextView addToFavourites = (TextView) findViewById(R.id.addAsFavourite);
        addToFavourites.setVisibility(visibility);
    }

    @Override
    public void displayToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
    }

    private void launchSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivityForResult(intent, SEARCH_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == SEARCH_CODE || requestCode == FAVOURITES_CODE) && resultCode == RESULT_OK) {
            lat = data.getDoubleExtra("latitude", lat);
            lon = data.getDoubleExtra("longitude", lon);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void fetchWeatherForLocation(double latitude, double longitude) {
        AsyncCurrentWeatherTask task = new AsyncCurrentWeatherTask(new AsyncCurrentWeatherTask.CurrentWeatherResponseListener() {
            @Override
            public void onCurrentWeatherFetched(Weather weather) {
                MainActivity.this.currentWeather = weather;
                renderWeatherList();
            }

            @Override
            public void onCurrentWeatherFetchFailure() {

            }
        });
        task.execute(latitude, longitude);
    }


    private void launchFavouritesActivity() {
        Intent intent = new Intent(this, FavouritesListActivity.class);
        startActivityForResult(intent, FAVOURITES_CODE);
    }
}

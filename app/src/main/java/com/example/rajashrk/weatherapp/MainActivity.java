package com.example.rajashrk.weatherapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajashrk.weatherapp.database.FavouritesRepository;
import com.example.rajashrk.weatherapp.model.Weather;
import com.example.rajashrk.weatherapp.model.WeatherForecastResponse;
import com.example.rajashrk.weatherapp.presenter.WeatherPresenter;
import com.example.rajashrk.weatherapp.tasks.AsyncWeatherForecastTask;
import com.example.rajashrk.weatherapp.tasks.CheckFavouriteAlreadyExistTask;
import com.example.rajashrk.weatherapp.tasks.SaveFavouriteTask;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements WeatherResponseListener, SaveFavouriteListener {

    private Weather currentWeather = null;
    private Map<String, Integer> weatherStatusImageMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWeatherStatusImageMap();
        showWeatherOfCurrentCity();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setWeatherStatusImageMap() {
        weatherStatusImageMap.put("light rain",R.drawable.rainy_main);
        weatherStatusImageMap.put("moderate rain",R.drawable.sun);
        weatherStatusImageMap.put("clear sky",R.drawable.sunny_main);
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
        new SaveFavouriteTask(favouritesRepository, this).execute(currentWeather.getName());
    }

    private void showWeatherOfCurrentCity() {
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("cityData.json");
            Gson gson = new Gson();
            Reader inputReader = new InputStreamReader(inputStream);
            currentWeather = gson.fromJson(inputReader, Weather.class);
            renderWeatherList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderWeatherList() {
        showFavouriteButtonIfNotSavedAsFavourite();

        WeatherPresenter presenter = new WeatherPresenter(currentWeather);
        TextView cityName = (TextView) findViewById(R.id.cityName);
        cityName.setText(currentWeather.getName());

        TextView temperature = (TextView) findViewById(R.id.temperature);
        temperature.setText(presenter.getTemperatureInCelsius());

        ImageView weatherImage = (ImageView) findViewById(R.id.weatherImage);
        weatherImage.setImageResource(weatherStatusImageMap.get(currentWeather.getWeather().get(0).getDescription()));

        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(weekday_name);
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
        Button favouriteButton = (Button) findViewById(R.id.favouriteButton);
        favouriteButton.setVisibility(visibility);
    }

    @Override
    public void displayToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
    }
}

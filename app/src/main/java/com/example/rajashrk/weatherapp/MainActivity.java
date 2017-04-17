package com.example.rajashrk.weatherapp;

import Tasks.AsyncWeatherForecastTask;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rajashrk.weatherapp.model.Weather;
import com.example.rajashrk.weatherapp.model.WeatherForecastResponse;
import com.example.rajashrk.weatherapp.presenter.WeatherPresenter;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements WeatherResponseListener {

    private  Weather currentWeather = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showWeatherOfCurrentCity();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void fetchForecast(View view) {
        String weatherUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + currentWeather.getName() + "&" + "APPID=" + "ebbc66b823072502c81339f5b0b9b042";
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
        Toast toast = Toast.makeText(this, "Failed to fetch weather forecast", Toast.LENGTH_LONG);
        toast.show();
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
        WeatherPresenter presenter = new WeatherPresenter(currentWeather);
        TextView cityName  = (TextView) findViewById(R.id.cityName);
        cityName.setText(currentWeather.getName());

        TextView temperature  = (TextView) findViewById(R.id.temperature);
        temperature.setText(presenter.getTemperatureIncelcius());


        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        TextView date  = (TextView) findViewById(R.id.date);
        date.setText(weekday_name);
    }

    private void showWeatherForecast(WeatherForecastResponse weatherList) {
        Intent intent = new Intent(this, DetailWeatherActivity.class);
        Gson gson = new Gson();
        String weatherData = gson.toJson(weatherList, WeatherForecastResponse.class);
        intent.putExtra("weatherData", weatherData);
        startActivity(intent);
    }
}

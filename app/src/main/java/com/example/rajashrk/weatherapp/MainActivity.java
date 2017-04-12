package com.example.rajashrk.weatherapp;

import Tasks.AsyncWeatherForecastTask;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rajashrk.weatherapp.adapter.WeatherListAdapter;
import com.example.rajashrk.weatherapp.model.Weather;
import com.example.rajashrk.weatherapp.model.WeatherForecastResponse;
import com.example.rajashrk.weatherapp.model.WeatherList;
import com.google.gson.Gson;

import Tasks.AsyncWeatherTask;

import java.io.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener ,WeatherResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadCitiesFromFile();
//        loadCities();
    }

    private void loadCitiesFromFile() {
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("cityData.json");
            Gson gson = new Gson();
            Reader inputReader = new InputStreamReader(inputStream);
            WeatherList weatherList = gson.fromJson(inputReader, WeatherList.class);
            renderWeatherList(weatherList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCities() {
        AsyncWeatherTask task = new AsyncWeatherTask(this);
        String weatherUrl = "http://api.openweathermap.org/data/2.5/box/city?bbox=72,10,86,30,6&cnt=10&appid=ebbc66b823072502c81339f5b0b9b042";
        task.execute(weatherUrl);
    }

    private void renderWeatherList(WeatherList weatherList) {
        WeatherListAdapter weatherListAdapter = new WeatherListAdapter(this, weatherList.getList());
        ListView view = (ListView) findViewById(R.id.city_list);
        view.setAdapter(weatherListAdapter);
        view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        WeatherListAdapter adapter = (WeatherListAdapter) parent.getAdapter();
        Weather weather = adapter.getItem(position);

        String weatherUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + weather.getName() + "&" + "APPID=" + "ebbc66b823072502c81339f5b0b9b042";
        AsyncWeatherForecastTask task = new AsyncWeatherForecastTask(this);
        task.execute(weatherUrl);
    }

    private void showWeatherForecast(WeatherForecastResponse weatherList) {
        Intent intent = new Intent(this, DetailWeatherActivity.class);
        Gson gson = new Gson();
        String weatherData = gson.toJson(weatherList, WeatherForecastResponse.class);
        intent.putExtra("weatherData", weatherData);
        startActivity(intent);
    }

    @Override
    public void weatherDataReceived(String data) {
        Gson gson = new Gson();
        WeatherList weatherList = gson.fromJson(data, WeatherList.class);
        renderWeatherList(weatherList);
    }

    @Override
    public void weatherForecastReceived(String data) {
        Gson gson = new Gson();
        WeatherForecastResponse weatherList = gson.fromJson(data, WeatherForecastResponse.class);
        showWeatherForecast(weatherList);
    }
}

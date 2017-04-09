package com.example.rajashrk.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rajashrk.weatherapp.adapter.WeatherListAdapter;
import com.example.rajashrk.weatherapp.model.Weather;
import com.example.rajashrk.weatherapp.model.WeatherList;
import com.google.gson.Gson;

import Tasks.AsyncWeatherTask;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener ,WeatherResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadCities();
    }

    private void loadCities() {
        String weatherUrl = "http://api.openweathermap.org/data/2.5/box/city?bbox=72,10,86,30,6&cnt=10&appid=ebbc66b823072502c81339f5b0b9b042";
        AsyncWeatherTask task = new AsyncWeatherTask(this);
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
        startDetailsActivity(weather);
//        String weatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + DataUtils.getCities().get(position) + "&" + "APPID=" + "ebbc66b823072502c81339f5b0b9b042";
//        AsyncWeatherTask task = new AsyncWeatherTask(this);
//        task.execute(weatherUrl);
    }

    private void startDetailsActivity(Weather weather) {
        Intent intent = new Intent(this, DetailWeatherActivity.class);
        Gson gson = new Gson();
        String weatherData = gson.toJson(weather, Weather.class);
        intent.putExtra("weatherData", weatherData);
        startActivity(intent);
    }

    @Override
    public void weatherDataReceived(String data) {
        Gson gson = new Gson();
        WeatherList weatherList = gson.fromJson(data, WeatherList.class);
        renderWeatherList(weatherList);
    }
}

package com.example.rajashrk.weatherapp;

public interface WeatherResponseListener {
    void weatherForecastReceived(String data);
    void weatherForecastFailed();
}

package com.example.rajashrk.weatherapp;

/**
 * Created by rajashrk on 4/8/17.
 */
public interface WeatherResponseListener {
    public void weatherDataReceived(String data);
    public void weatherForecastReceived(String data);
}

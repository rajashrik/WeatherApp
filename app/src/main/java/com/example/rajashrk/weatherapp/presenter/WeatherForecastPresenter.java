package com.example.rajashrk.weatherapp.presenter;

import com.example.rajashrk.weatherapp.model.WeatherForecast;

public class WeatherForecastPresenter {
    private WeatherForecast weather;

    public WeatherForecastPresenter(WeatherForecast weather) {
        super();
        this.weather = weather;
    }

    public String getTemperatureMinInCelsius() {
        return String.valueOf(weather.getTemperature().getMinimun()) + "\u00b0" + "C";
    }

    public String getTemperatureMaxInCelsius() {
        return String.valueOf(weather.getTemperature().getMaximum()) + "\u00b0" + "C";
    }
}

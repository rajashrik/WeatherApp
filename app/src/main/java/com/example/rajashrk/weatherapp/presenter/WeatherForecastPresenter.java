package com.example.rajashrk.weatherapp.presenter;

import com.example.rajashrk.weatherapp.R;
import com.example.rajashrk.weatherapp.model.Weather;
import com.example.rajashrk.weatherapp.model.WeatherForecast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rajashrk on 4/9/17.
 */


public class WeatherForecastPresenter {
    private WeatherForecast weather;

    public WeatherForecastPresenter(WeatherForecast weather) {
        super();
        this.weather = weather;
    }

    public String getTemperatureMinIncelcius() {
        return String.valueOf(kelvinToCelciusConvert(weather.getTemperature().getMinimun())) + "\u00b0" + "C";
    }

    public String getTemperatureMaxIncelcius() {
        return String.valueOf(kelvinToCelciusConvert(weather.getTemperature().getMaximum())) + "\u00b0" + "C";
    }

    private Double kelvinToCelciusConvert(Float temp) {
        return  Math.floor(temp - 273.15);
    }

}

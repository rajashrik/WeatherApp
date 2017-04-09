package com.example.rajashrk.weatherapp.presenter;

import com.example.rajashrk.weatherapp.R;
import com.example.rajashrk.weatherapp.model.Weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rajashrk on 4/9/17.
 */
public class WeatherPresenter {
    private Weather weather;

    public WeatherPresenter(Weather weather) {
        super();
        this.weather = weather;
    }

    public String getTemperature() {
        return String.valueOf(weather.getMain().getTemp()) + "\u00b0" + "C";
    }

    public String getPressure() {
        return String.valueOf(weather.getMain().getPressure()) + "hpa";
    }

    public String getHumidity() {
        return String.valueOf(weather.getMain().getHumidity()) + "%";
    }

    public String getDescription(   ){
        return weather.getWeather().get(0).getDescription();
    }


}

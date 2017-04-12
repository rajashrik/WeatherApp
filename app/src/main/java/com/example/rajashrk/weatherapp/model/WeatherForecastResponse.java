package com.example.rajashrk.weatherapp.model;

import java.util.List;

/**
 * Created by rajashrk on 4/12/17.
 */
public class WeatherForecastResponse {

    private City city;
    private List<WeatherForecast> list;

    public String getCityName() {
        return city.name;
    }
    public List<WeatherForecast> getList() {
        return list;
    }

    public class City {
        private long id;
        private String name;
        private String country;

        public String getCountry() {
            return country;
        }
    }

}

package com.example.rajashrk.weatherapp.model;

import java.util.List;

/**
 * Created by rajashrk on 4/12/17.
 */
public class WeatherForecast {

    private List<Weather> weather;
    private Temperature temp;

    public Temperature getTemperature() {
        return temp;
    }

    public Weather getWeather() {
        return weather.get(0);
    }


    public class Temperature {
        private float min;
        private float max;

        public float getMinimun() {
            return min;
        }

        public float getMaximum() {
            return max;
        }
    }

    public  class Weather {
        private int id;
        private String description;
        private String main;

        public String getDescription() {
            return description;
        }

        public String getMain(){
            return main;
        }
    }


}

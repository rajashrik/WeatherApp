package com.example.rajashrk.weatherapp.presenter;

import com.example.rajashrk.weatherapp.model.Weather;

public class WeatherPresenter {
        private Weather weather;

        public WeatherPresenter(Weather weather) {
            super();
            this.weather = weather;
        }

        public String getTemperatureInCelsius() {
            return String.valueOf(weather.getMain().getTemp()) + "\u00b0" + "C";
        }

}

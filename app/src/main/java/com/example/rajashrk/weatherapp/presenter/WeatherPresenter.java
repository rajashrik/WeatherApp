package com.example.rajashrk.weatherapp.presenter;

import com.example.rajashrk.weatherapp.model.Weather;
import com.example.rajashrk.weatherapp.model.WeatherForecast;

/**
 * Created by rajashrk on 4/14/17.
 */
public class WeatherPresenter {
        private Weather weather;

        public WeatherPresenter(Weather weather) {
            super();
            this.weather = weather;
        }

        public String getTemperatureIncelcius() {
            return String.valueOf(kelvinToCelciusConvert(weather.getMain().getTemp())) + "\u00b0" + "C";
        }
        private Double kelvinToCelciusConvert(Float temp) {
            return  Math.floor(temp - 273.15);
        }

}

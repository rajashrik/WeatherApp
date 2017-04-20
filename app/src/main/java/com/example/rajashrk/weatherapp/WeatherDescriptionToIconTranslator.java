package com.example.rajashrk.weatherapp;

import java.util.HashMap;
import java.util.Map;

public class WeatherDescriptionToIconTranslator {

    private static final Map<String, WeatherImageHolder> weatherStatusImageMap;

    static {
        weatherStatusImageMap = new HashMap<>();
        weatherStatusImageMap.put("light rain", new WeatherImageHolder(R.drawable.rainy_main, R.drawable.rain));
        weatherStatusImageMap.put("moderate rain", new WeatherImageHolder(R.drawable.sunny_main, R.drawable.sun));
        weatherStatusImageMap.put("clear sky", new WeatherImageHolder(R.drawable.sunny_main, R.drawable.sun));
        weatherStatusImageMap.put("sky is clear", new WeatherImageHolder(R.drawable.sunny_main, R.drawable.sun));
        weatherStatusImageMap.put("broken clouds", new WeatherImageHolder(R.drawable.cloud, R.drawable.cloud));
        weatherStatusImageMap.put("haze", new WeatherImageHolder(R.drawable.cloud, R.drawable.cloud));
    }

    int getDetailImageResourceId(String description) {
        if (weatherStatusImageMap.containsKey(description)) {
            return weatherStatusImageMap.get(description).detailImageResourceId;
        }
        return R.drawable.sunny_main;
    }

    public int getListImageResourceId(String description) {
        if (weatherStatusImageMap.containsKey(description)) {
            return weatherStatusImageMap.get(description).listImageResourceId;
        }
        return R.drawable.sun;
    }

    private static class WeatherImageHolder {
        private final int detailImageResourceId;
        private final int listImageResourceId;

        private WeatherImageHolder(int detailImageResourceId, int listImageResourceId) {
            this.detailImageResourceId = detailImageResourceId;
            this.listImageResourceId = listImageResourceId;
        }
    }

}

package com.example.rajashrk.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.rajashrk.weatherapp.R;
import com.example.rajashrk.weatherapp.model.WeatherForecast;
import com.example.rajashrk.weatherapp.presenter.WeatherForecastPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rajashrk on 4/12/17.
 */
public class WeatherForecastAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<WeatherForecast> weatherList;
    private Map<String, Integer> weatherIconsList = new HashMap<>();

    public WeatherForecastAdapter(Context context, List<WeatherForecast> weatherList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.weatherList = weatherList;
        constructweatherIconMap();
    }

    private void constructweatherIconMap() {
        weatherIconsList.put("light rain",R.drawable.rain);
        weatherIconsList.put("moderate rain",R.drawable.sun);
        weatherIconsList.put("sky is clear",R.drawable.cloud);
    }


    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public WeatherForecast getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = createView();
        }
        updateView(convertView, position);
        return convertView;
    }

    private class ViewHolder {
        TextView day;
        TextView temperature_max;
        TextView temperature_min;
        TextView up_arrow;
        ImageView weatherImg;

    }

    private void updateView(View view, int position) {
        WeatherForecastAdapter.ViewHolder viewHolder = (WeatherForecastAdapter.ViewHolder) view.getTag();
        WeatherForecast weather = weatherList.get(position);
        WeatherForecastPresenter presenter = new WeatherForecastPresenter(weather);
        viewHolder.temperature_max.setText(presenter.getTemperatureMaxInCelsius());
        viewHolder.temperature_min.setText(presenter.getTemperatureMinInCelsius());
        viewHolder.day.setText(presenter.getDayOfTheWeek());
        viewHolder.up_arrow.setText(R.string.upArrow);
        viewHolder.weatherImg.setImageResource(getWeatherIcon(weatherList.get(position).getWeather().getDescription()));
    }

    private int getWeatherIcon(String weatherStatus) {
        return weatherIconsList.get(weatherStatus);
    }

    private View createView() {
        View view = inflater.inflate(R.layout.list_weather_details_item, null);
        WeatherForecastAdapter.ViewHolder viewHolder = new WeatherForecastAdapter.ViewHolder();
        viewHolder.temperature_max = (TextView) view.findViewById(R.id.temperature_max);
        viewHolder.day = (TextView) view.findViewById(R.id.day);
        viewHolder.temperature_min = (TextView) view.findViewById(R.id.temperature_min);
        viewHolder.up_arrow = (TextView) view.findViewById(R.id.temperature_max_arrow);
        viewHolder.weatherImg = (ImageView) view.findViewById(R.id.weatherimg);
        view.setTag(viewHolder);
        return view;
    }
}


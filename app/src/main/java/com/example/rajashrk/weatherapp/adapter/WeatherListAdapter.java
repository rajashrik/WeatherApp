package com.example.rajashrk.weatherapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rajashrk.weatherapp.R;
import com.example.rajashrk.weatherapp.model.Weather;
import com.example.rajashrk.weatherapp.model.WeatherList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jhansirk on 09/04/17.
 */
public class WeatherListAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    private List<Weather> weatherList;

    public WeatherListAdapter(Context context, List<Weather> weatherList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.weatherList = weatherList;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
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

    private void updateView(View view, int position) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Weather weather = weatherList.get(position);
        viewHolder.cityName.setText(weather.getName());
        String temperature = String.valueOf(weather.getMain().getTemp());
        viewHolder.temperature.setText(temperature);
        String description = weather.getWeather().get(0).getDescription();
        viewHolder.weatherDescription.setText(description);
        viewHolder.weatherIcon.setImageResource(getWeatherIcon(description));
    }

    private int getWeatherIcon(String description) {
        Map<String, Integer> weatherIcons = new HashMap<>();
        weatherIcons.put("haze",R.drawable.weather_snowy);
        weatherIcons.put("few clouds",R.drawable.weather_cloudy);
        weatherIcons.put("broken cloouds",R.drawable.weather_cloudy);
        weatherIcons.put("broken cloouds",R.drawable.weather_cloudy);
        weatherIcons.put("clear sky",R.drawable.weather_sunny);
        if(weatherIcons.containsKey(description))
        {
            return weatherIcons.get(description);
        }
        return R.drawable.weather_rainy;
    }

    private class ViewHolder{
        TextView cityName;
        TextView temperature;
        TextView weatherDescription;
        ImageView weatherIcon;
    }

    private View createView() {
        View view = inflater.inflate(R.layout.list_item, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.cityName = (TextView) view.findViewById(R.id.cityName);
        viewHolder.temperature = (TextView) view.findViewById(R.id.temperature);
        viewHolder.weatherDescription = (TextView) view.findViewById(R.id.weatherDescription);
        viewHolder.weatherIcon = (ImageView) view.findViewById(R.id.weatherIcon);

        view.setTag(viewHolder);
        return view;
    }
}

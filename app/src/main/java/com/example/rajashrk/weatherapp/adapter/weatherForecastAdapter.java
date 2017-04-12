package com.example.rajashrk.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.rajashrk.weatherapp.R;
import com.example.rajashrk.weatherapp.model.Weather;
import com.example.rajashrk.weatherapp.model.WeatherForecast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rajashrk on 4/12/17.
 */
public class weatherForecastAdapter  extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<WeatherForecast> weatherList;

    public weatherForecastAdapter(Context context, List<WeatherForecast> weatherList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.weatherList = weatherList;
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
        TextView cityName;
        TextView temperature_max;
        TextView temperature_min;
    }

    private void updateView(View view, int position) {
        weatherForecastAdapter.ViewHolder viewHolder = (weatherForecastAdapter.ViewHolder) view.getTag();
        WeatherForecast weather = weatherList.get(position);
        String temperature = String.valueOf(weather.getTemperature().getMinimun());
        viewHolder.temperature_max.setText(temperature);

    }

    private View createView() {
        View view = inflater.inflate(R.layout.list_weather_details_item, null);
        weatherForecastAdapter.ViewHolder viewHolder = new weatherForecastAdapter.ViewHolder();
        viewHolder.cityName = (TextView) view.findViewById(R.id.cityName);
        viewHolder.temperature_max = (TextView) view.findViewById(R.id.temperature_max);

        view.setTag(viewHolder);
        return view;
    }
}


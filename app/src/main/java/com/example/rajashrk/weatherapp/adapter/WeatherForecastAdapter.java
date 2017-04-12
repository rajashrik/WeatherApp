package com.example.rajashrk.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.rajashrk.weatherapp.R;
import com.example.rajashrk.weatherapp.model.WeatherForecast;
import com.example.rajashrk.weatherapp.presenter.WeatherForecastPresenter;

import java.util.List;

/**
 * Created by rajashrk on 4/12/17.
 */
public class WeatherForecastAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<WeatherForecast> weatherList;

    public WeatherForecastAdapter(Context context, List<WeatherForecast> weatherList) {
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
        WeatherForecastAdapter.ViewHolder viewHolder = (WeatherForecastAdapter.ViewHolder) view.getTag();
        WeatherForecast weather = weatherList.get(position);
        WeatherForecastPresenter presenter = new WeatherForecastPresenter(weather);
        viewHolder.temperature_max.setText(presenter.getTemperatureMaxIncelcius());
        viewHolder.temperature_min.setText(presenter.getTemperatureMinIncelcius());

    }

    private View createView() {
        View view = inflater.inflate(R.layout.list_weather_details_item, null);
        WeatherForecastAdapter.ViewHolder viewHolder = new WeatherForecastAdapter.ViewHolder();
        viewHolder.cityName = (TextView) view.findViewById(R.id.cityName);
        viewHolder.temperature_max = (TextView) view.findViewById(R.id.temperature_max);
        viewHolder.temperature_min = (TextView) view.findViewById(R.id.temperature_min);

        view.setTag(viewHolder);
        return view;
    }
}


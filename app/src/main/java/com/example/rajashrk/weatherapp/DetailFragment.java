package com.example.rajashrk.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.rajashrk.weatherapp.model.Weather;
import com.example.rajashrk.weatherapp.presenter.WeatherPresenter;
import com.google.gson.Gson;


public class DetailFragment extends Fragment {

    private String cityName;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        String details = getArguments().getString("details");
        Gson jason = new Gson();
        Weather weather = jason.fromJson(details,Weather.class);
        WeatherPresenter presenter = new WeatherPresenter(weather);

        TextView city = (TextView)view.findViewById(R.id.city);
        city.setText(weather.getName());


        TextView temp = (TextView) view.findViewById(R.id.temperature);
        temp.setText(presenter.getTemperature());

        TextView description = (TextView) view.findViewById(R.id.description);
        description.setText(presenter.getDescription());

        TextView humidity = (TextView) view.findViewById(R.id.humidity);
        humidity.setText(presenter.getHumidity());

        TextView pressure = (TextView) view.findViewById(R.id.pressure);
        pressure.setText(presenter.getPressure());

        return view;
    }


}

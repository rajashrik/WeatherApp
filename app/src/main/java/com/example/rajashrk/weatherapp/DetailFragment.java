package com.example.rajashrk.weatherapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.rajashrk.weatherapp.adapter.weatherForecastAdapter;
import com.example.rajashrk.weatherapp.model.WeatherForecastResponse;
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
        WeatherForecastResponse weatherList = jason.fromJson(details,WeatherForecastResponse.class);

        weatherForecastAdapter weatherForecastAdapter = new weatherForecastAdapter(getActivity(), weatherList.getList());
        ListView listView = (ListView) view.findViewById(R.id.weather_forecast);
        listView.setAdapter(weatherForecastAdapter);

        return view;
    }


}

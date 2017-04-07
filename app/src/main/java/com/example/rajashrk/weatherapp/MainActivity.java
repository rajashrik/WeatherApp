package com.example.rajashrk.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] cityArray = {"Hyderabad", "Mumbai", "Delhi", "Patna", "Nasik", "Nagpur", "Kolkata"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showCityListView();
    }

    private void showCityListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, cityArray);
        ListView view = (ListView) findViewById(R.id.city_list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(this,DetailWeatherActivity.class);
            startActivity(intent);
    }
}

package com.example.rajashrk.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String[] cityArray = {"Hyderabd","Mumbai","Delhi"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,cityArray);

        ListView view = (ListView) findViewById(R.id.city_list);
        view.setAdapter(adapter);
    }

}

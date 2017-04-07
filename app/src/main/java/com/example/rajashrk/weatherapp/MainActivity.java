package com.example.rajashrk.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String[] cityArray = {"Hyderabad","Mumbai","Delhi","Patna","Nasik","Nagpur","Kolkata"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpListView();
    }

    private void setUpListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,cityArray);

        ListView view = (ListView) findViewById(R.id.city_list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Item Clicked is", cityArray[position]);
            }
        });
    }


}

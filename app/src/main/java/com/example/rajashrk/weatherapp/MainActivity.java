package com.example.rajashrk.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ListItemOnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showCityListView();
    }

    private void showCityListView() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new ListFragment();
        transaction.add(R.id.container, fragment, "list");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onListItemClick(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new DetailFragment();
        transaction.add(R.id.container, fragment, "details");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

package com.example.rajashrk.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rajashrk.weatherapp.database.FavouritesRepository;
import com.example.rajashrk.weatherapp.model.FavouriteModel;

import java.util.ArrayList;
import java.util.List;

public class FavouritesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        initialiseViews();
        initialiseToolBar();
    }

    private void initialiseViews() {
        ListView favouritesListView = (ListView) findViewById(R.id.favouritesListView);

        FavouritesRepository favouritesRepository = new FavouritesRepository(getApplicationContext());
        List<FavouriteModel> favouriteModelList = favouritesRepository.getFavourites();

        FavouritesListAdapter favouritesListAdapter = new FavouritesListAdapter(this, new ArrayList<FavouriteModel>(favouriteModelList));
        favouritesListView.setAdapter(favouritesListAdapter);

        favouritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                FavouriteModel favouriteModel = (FavouriteModel) adapterView.getItemAtPosition(position);
                loadForecastForFavourite(favouriteModel);
            }
        });
    }


    private void initialiseToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setTitle("My Favourites");
    }

    private void loadForecastForFavourite(FavouriteModel favouriteModel) {
        Intent intent = new Intent();
        intent.putExtra("latitude", favouriteModel.getLatitude());
        intent.putExtra("longitude", favouriteModel.getLongitude());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private static class FavouritesListAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private List<FavouriteModel> favouritesList;

        FavouritesListAdapter(Context context, List<FavouriteModel> favouritesList) {
            inflater = LayoutInflater.from(context);
            this.favouritesList = favouritesList;
        }

        @Override
        public int getCount() {
            return favouritesList.size();
        }

        @Override
        public FavouriteModel getItem(int position) {
            return favouritesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = createView();
            }
            updateView(convertView, position);
            return convertView;
        }


        private void updateView(View view, int position) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            FavouriteModel favourite = favouritesList.get(position);
            viewHolder.cityName.setText(favourite.getCityName());
        }

        private View createView() {
            View view = inflater.inflate(R.layout.list_search_details_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.cityName = (TextView) view.findViewById(R.id.cityNameTextView);
            view.setTag(viewHolder);
            return view;
        }

        private class ViewHolder {
            TextView cityName;
        }
    }
}
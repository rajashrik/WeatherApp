package com.example.rajashrk.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajashrk.weatherapp.model.SearchResult;
import com.example.rajashrk.weatherapp.tasks.SearchCitiesTask;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchCitiesTask.SearchResultsListener {

    private EditText searchCityEditText;
    private SearchResultsListAdapter searchResultsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initialiseViews();
    }

    private void initialiseViews() {
        searchCityEditText = (EditText) findViewById(R.id.searchCityEditText);
        ImageView searchImageView = (ImageView) findViewById(R.id.searchImageView);
        ListView searchResultsListView = (ListView) findViewById(R.id.searchResultsListView);

        searchResultsListAdapter = new SearchResultsListAdapter(this, new ArrayList<SearchResult.SearchCityDetails>());
        searchResultsListView.setAdapter(searchResultsListAdapter);

        searchCityEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });

        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SearchResult.SearchCityDetails searchCityDetails = (SearchResult.SearchCityDetails) adapterView.getItemAtPosition(position);
                loadForecastForSearch(searchCityDetails.getCoord());
            }
        });
    }

    private void performSearch() {
        String queryText = searchCityEditText.getText().toString();
        if (queryText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter search text", Toast.LENGTH_LONG).show();
            return;
        }
        searchCitiesWithText(queryText);
    }

    private void loadForecastForSearch(SearchResult.LatLng latLng) {
        Intent intent = new Intent();
        intent.putExtra("latitude", latLng.getLat());
        intent.putExtra("longitude", latLng.getLon());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void searchCitiesWithText(String queryText) {
        SearchCitiesTask searchCitiesTask = new SearchCitiesTask(this);
        searchCitiesTask.execute(queryText);
    }


    @Override
    public void onSearchResultsFetched(SearchResult searchResult) {
        searchResultsListAdapter.updateSearchResults(searchResult);
    }

    @Override
    public void onSearchResultsFetchFailed() {

    }

    private static class SearchResultsListAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private List<SearchResult.SearchCityDetails> searchCityDetailsList;

        SearchResultsListAdapter(Context context, List<SearchResult.SearchCityDetails> searchCityDetailsList) {
            inflater = LayoutInflater.from(context);
            this.searchCityDetailsList = searchCityDetailsList;
        }

        @Override
        public int getCount() {
            return searchCityDetailsList.size();
        }

        @Override
        public SearchResult.SearchCityDetails getItem(int position) {
            return searchCityDetailsList.get(position);
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

        void updateSearchResults(SearchResult searchResult) {
            this.searchCityDetailsList = searchResult.getList();
            this.notifyDataSetChanged();
        }

        private void updateView(View view, int position) {
            SearchResultViewHolder searchResultViewHolder = (SearchResultViewHolder) view.getTag();
            SearchResult.SearchCityDetails searchCityDetails = searchCityDetailsList.get(position);
            searchResultViewHolder.cityName.setText(searchCityDetails.getName() + ", " + searchCityDetails.getCountryName());
        }

        private View createView() {
            View view = inflater.inflate(R.layout.list_search_details_item, null);
            SearchResultViewHolder searchResultViewHolder = new SearchResultViewHolder();
            searchResultViewHolder.cityName = (TextView) view.findViewById(R.id.cityNameTextView);
            view.setTag(searchResultViewHolder);
            return view;
        }

        private class SearchResultViewHolder {
            TextView cityName;
        }
    }
}

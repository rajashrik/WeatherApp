package com.example.rajashrk.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by rajashrk on 4/7/17.
 */
public class ListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String[] cityArray = {"Hyderabad", "Mumbai", "Delhi", "Patna", "Nasik", "Nagpur", "Kolkata"};
    private ListItemOnClickListener listener;

    public ListFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, cityArray);
        ListView view = (ListView) v.findViewById(R.id.city_list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListItemOnClickListener){
            listener = (ListItemOnClickListener) context;
        } else {
            throw new RuntimeException("null ListItemOnClickListener ");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.onListItemClick(position);
    }
}

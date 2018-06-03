package com.example.hyo_jin.themostcleversubway.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.hyo_jin.themostcleversubway.Adapter.HistoryChildAdapter;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestFragment extends Fragment {
    private static final String TAG = "TestFragment";

    private ExpandableListView expandableListView;

    String[] arProv = new String[] { "ㄱ", "ㄴ", "ㄷ" };
    String[][] arCity = new String[][] {
            {"A", "B", "C"},
            {"D", "E"},
            {"F", "G", "I"}
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        init(view);

        List<String> provData = new ArrayList<>();
        Map<String, List<String>> cityData = new HashMap<>();

        // Adding Group Data
        for (int i = 0; i < arProv.length; i++) {
            provData.add(arProv[i]);

            // Adding Child Data
            List<String> city = new ArrayList<>();
            for (int j = 0; j < arCity[i].length; j++) {
                city.add(arCity[i][j]);
            }
            cityData.put(arProv[i], city);
        }

        HistoryChildAdapter adapter = new HistoryChildAdapter(getContext(), provData, cityData);

        Log.v(TAG, expandableListView == null ? "Null" : "Not Null");

        expandableListView.setAdapter(adapter);

        return view;
    }

    protected void init(View view) {
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandablelistview_history);
    }
}

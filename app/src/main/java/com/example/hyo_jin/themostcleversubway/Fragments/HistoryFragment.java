package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hyo_jin.themostcleversubway.Activity.TimetablePopup;
import com.example.hyo_jin.themostcleversubway.Adapter.HistoryExpandableListAdapter;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryFragment extends Fragment {
    private static final String TAG = "HistoryFragment";

    private View result;
    private ExpandableListView expandableListView;
    private ImageButton btn_timetable;

    String[] arProv = new String[] { "ㄱ", "ㄴ", "ㄷ" };
    String[][] arCity = new String[][] {
            {"A"},
            {"B"},
            {"C"}
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        init(view);

        /*** 익스팬더블 리스트뷰 완성하기 ***/
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

        HistoryExpandableListAdapter adapter = new HistoryExpandableListAdapter(getContext(), provData, cityData);

        Log.v(TAG, expandableListView == null ? "Null" : "Not Null");

        expandableListView.setAdapter(adapter);
        /************************************/

        Log.v(TAG, btn_timetable == null ? "Null" : "Not Null");

        btn_timetable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v(TAG, "클릭됨");
                Toast.makeText(getActivity(), TAG + " 프래그먼트에서 시간표 액티비티를 띄워봄", Toast.LENGTH_LONG).show();
                startActivity(new Intent(HistoryFragment.super.getContext(), TimetablePopup.class));
            }
        });

        return view;
    }

    protected void init(View view) {
        result = getLayoutInflater().inflate(R.layout.fragment_result, null, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandablelistview_history);
        btn_timetable = (ImageButton) result.findViewById(R.id.btn_timetable);
        //Log.v(TAG, String.valueOf(result.findViewById(R.id.btn_timetable)));
    }

}

package com.example.hyo_jin.themostcleversubway.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hyo_jin.themostcleversubway.Adapter.TestAdapter;
import com.example.hyo_jin.themostcleversubway.Item.ResultItem;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.List;


public class TestFragment extends Fragment {

    private static final String TAG = "TestFragment";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ListView listview_test;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        init(view);

        String[] lefttime = {"3분 12초", "6분 9초"};

        List<ResultItem> result = new ArrayList<>();
        ResultItem item1 = new ResultItem(1, "구파발", "대화행", lefttime, -1, null, null);
        ResultItem item2 = new ResultItem(2, null, null, null, 4, "2-6", "8-4");
        ResultItem item3 = new ResultItem(1, "약수역", "봉화산행", lefttime, -1, null, null);
        ResultItem item4 = new ResultItem(2, null, null, null, 4, "2-6", "8-4");
        ResultItem item5 = new ResultItem(3, "태릉입구역", null, lefttime, -1, null, null);

        result.add(item1);
        result.add(item2);
        result.add(item3);
        result.add(item4);
        result.add(item5);

        TestAdapter adapter = new TestAdapter(getContext(), result);

        listview_test.setAdapter(adapter);

        /*

        FavoriteGridAdapter adapter = new FavoriteGridAdapter(this.getContext(), R.layout.gridlayout_favorite, data);
        gridView.setAdapter(adapter);
        * */


        // use a linear layout manager
        /*layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new MyAdapter(station_dep, station_arr, station_trans, direction, left, bestseat, fastseat);
        recyclerView.setAdapter(adapter);*/

        return view;
    }

    protected void init(View view) {
        //recyclerView = (RecyclerView) view.findViewById(R.id.test_recycler);
        listview_test = (ListView) view.findViewById(R.id.listview_test);
    }

}

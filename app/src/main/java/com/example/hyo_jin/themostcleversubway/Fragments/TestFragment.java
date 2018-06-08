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

import com.example.hyo_jin.themostcleversubway.Adapter.MyAdapter;
import com.example.hyo_jin.themostcleversubway.Adapter.TestAdapter;
import com.example.hyo_jin.themostcleversubway.Item.FavoriteGridItem;
import com.example.hyo_jin.themostcleversubway.Item.ResultItem;
import com.example.hyo_jin.themostcleversubway.Item.ResultItem1;
import com.example.hyo_jin.themostcleversubway.Item.ResultItem2;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.List;


public class TestFragment extends Fragment {

    private static final String TAG = "TestFragment";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ListView listview_test;

    private String station_dep, station_arr;
    private String[] station_trans;
    private String direction;
    private int left;
    private String bestseat, fastseat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        init(view);

        String[] lefttime = {"3분 12초", "6분 9초"};
        /*
        List<ResultItem1> stationData = new ArrayList<>();
        List<ResultItem2> transferData = new ArrayList<>();

        ResultItem1 station1 = new ResultItem1("구파발", "오금행", lefttime);
        ResultItem1 station2 = new ResultItem1("약수", "봉화산행", lefttime);
        ResultItem1 station3 = new ResultItem1("태릉입구", "오금행", lefttime);

        stationData.add(station1);
        stationData.add(station2);
        stationData.add(station3);

        ResultItem2 transfer1 = new ResultItem2(6, "4-2", "6-4");
        ResultItem2 transfer2 = new ResultItem2(8, "4-3", "6-4");

        transferData.add(transfer1);
        transferData.add(transfer2);
*/
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

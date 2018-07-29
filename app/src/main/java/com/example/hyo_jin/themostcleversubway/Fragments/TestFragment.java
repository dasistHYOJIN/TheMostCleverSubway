package com.example.hyo_jin.themostcleversubway.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.hyo_jin.themostcleversubway.Adapter.RecyclerviewAdapter;
import com.example.hyo_jin.themostcleversubway.Item.ResultItem;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.List;


public class TestFragment extends Fragment {

    private static final String TAG = "TestFragment";

/*    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;*/
    private ProgressBar progressBar;

    private int progressStatus = 0;

    List<ResultItem> result = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        init(view);

        progressBar.setProgress(87);
/*        recyclerView.setHasFixedSize(true);

        String[] lefttime = {"3분 12초", "6분 9초"};
        result.add(new ResultItem(1, "구파발", "대화행", lefttime, -1, null, null));
        result.add(new ResultItem(2, null, null, null, 5, "2-6", "8-4"));
        result.add(new ResultItem(1, "약수", "봉화산행", lefttime, -1, null, null));
        result.add(new ResultItem(2, null, null, null, 3, "2-6", "8-4"));
        result.add(new ResultItem(3, "태릉입구", "", lefttime, -1, null, null));

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new RecyclerviewAdapter(result);
        recyclerView.setAdapter(adapter);*/

        return view;
    }

    protected void init(View view) {
        //recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

}

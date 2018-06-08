package com.example.hyo_jin.themostcleversubway.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hyo_jin.themostcleversubway.R;

/**
 * Created by Hyo-Jin on 2018-06-04.
 * : a simple implemantation for a data set that consists of an array of strings displayed using TextView widgets
 * https://developer.android.com/guide/topics/ui/layout/recyclerview#java
 */

public class MyAdapter extends RecyclerView.Adapter {

    private String station_dep, station_arr;
    private String[] station_trans;
    private String direction;
    private int left;
    private String bestseat, fastseat;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_result, parent, false);

        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        public ListItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}

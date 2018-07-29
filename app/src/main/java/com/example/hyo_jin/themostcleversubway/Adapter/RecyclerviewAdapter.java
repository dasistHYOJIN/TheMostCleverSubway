package com.example.hyo_jin.themostcleversubway.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hyo_jin.themostcleversubway.Item.ResultItem;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.List;
import java.util.Random;

/**
 * Created by Hyo-Jin on 2018-06-04.
 * : a simple implemantation for a data set that consists of an array of strings displayed using TextView widgets
 * https://developer.android.com/guide/topics/ui/layout/recyclerview#java
 *
 * : Adapter which binds result data is used @ Recycler View
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter {

    final String TAG = "RecyclerviewAdapter";

    private List<ResultItem> result;

    /****** View Holder ******/
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageButton btn_timetable;
        ImageView img_linecolor;
        TextView station_name, direction, lefttime;

        TextView left, bestseat, fastseat;
        ProgressBar progressBar;

        public ViewHolder(View itemView, int type) {
            super(itemView);
            if (type == 1 || type == 3) {
                this.btn_timetable = (ImageButton) itemView.findViewById(R.id.btn_timetable);
                this.img_linecolor = (ImageView) itemView.findViewById(R.id.img_linecolor);
                this.station_name = (TextView) itemView.findViewById(R.id.station_name);
                this.direction = (TextView) itemView.findViewById(R.id.direction);
                this.lefttime = (TextView) itemView.findViewById(R.id.lefttime);
            } else if (type == 2) {
                this.left = (TextView) itemView.findViewById(R.id.left);
                this.progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
                this.bestseat = (TextView) itemView.findViewById(R.id.bestseat);
                this.fastseat = (TextView) itemView.findViewById(R.id.fastseat);
            }

        }
    }
    /****** View Holder ******/

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerviewAdapter(List<ResultItem> result) {
        this.result = result;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlayout_result1, parent, false);
                viewHolder = new ViewHolder(view, 1);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlayout_result2, parent, false);
                viewHolder = new ViewHolder(view, 2);
                break;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlayout_result1, parent, false);
                viewHolder = new ViewHolder(view, 3);
                break;
        }

        return (ViewHolder) viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    // 생성된 viewHolder와 position을 전달받아서,
    // 현재 position에 맞는 data를 viewHolder가 관리하는 view들에 binding
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        int res = result.get(position).getType();
        switch (res) {
            case 1:
                ((ViewHolder) holder).station_name.setText(result.get(position).getStation());
                ((ViewHolder) holder).direction.setText(result.get(position).getDirection());
                ((ViewHolder) holder).lefttime.setText(result.get(position).getTime()[0] + " 후 도착\n"
                        + result.get(position).getTime()[1] + " 후 도착");

                break;
            case 2:
                int rand = new Random().nextInt()%100;
                rand *= (rand < 0) ? -1 : 1;
                ((ViewHolder) holder).left.setText("+" + result.get(position).getLeft());
                ((ViewHolder) holder).progressBar.setProgress(rand);
                Log.v(TAG, "" + rand);
                ((ViewHolder) holder).bestseat.setText(result.get(position).getCell_best());
                ((ViewHolder) holder).fastseat.setText(result.get(position).getCell_fast());

                break;
            case 3:
                ((ViewHolder) holder).station_name.setText(result.get(position).getStation());
                ((ViewHolder) holder).direction.setText(result.get(position).getDirection());
                ((ViewHolder) holder).lefttime.setText(result.get(position).getTime()[0] + " 후 도착\n"
                        + result.get(position).getTime()[1] + " 후 도착");
                break;

        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    @Override
    public int getItemViewType(int position) {
        return result.get(position).getType();
    }
}

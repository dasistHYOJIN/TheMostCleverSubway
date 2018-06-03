package com.example.hyo_jin.themostcleversubway.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hyo_jin.themostcleversubway.Item.FavoriteGridItem;
import com.example.hyo_jin.themostcleversubway.Item.HistoryListItem;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyo-Jin on 2018-05-16.
 */

public class HistoryListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<HistoryListItem> data = new ArrayList<HistoryListItem>();
    private int layout;

    public HistoryListAdapter(Context context, int layout, List<HistoryListItem> data) {
        //this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if(view == null)
            view = inflater.inflate(layout, viewGroup, false);

        HistoryListItem historyListItem = data.get(i);

        TextView text_course = (TextView) view.findViewById(R.id.text_course_history);
        //ImageButton image_star = (ImageButton) view.findViewById(R.id.image_star);

        // 아이템 내 각 위젯에 데이터 반영
        text_course.setText(historyListItem.getStation1() + " → " + historyListItem.getStation2());
        //image_star.setImageResource();

        return view;
    }
}

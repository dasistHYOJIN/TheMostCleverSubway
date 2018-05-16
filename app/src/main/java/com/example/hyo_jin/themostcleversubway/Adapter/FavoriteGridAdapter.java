package com.example.hyo_jin.themostcleversubway.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hyo_jin.themostcleversubway.Item.FavoriteGridItem;
import com.example.hyo_jin.themostcleversubway.Item.HistoryListItem;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.hyo_jin.themostcleversubway.R.id.parent;

/**
 * Created by Hyo-Jin on 2018-05-14.
 * 즐겨찾기 그리드 어댑터
 */

public class FavoriteGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<FavoriteGridItem> data = new ArrayList<FavoriteGridItem>();
    private int layout;

    public FavoriteGridAdapter(Context context, int layout, List<FavoriteGridItem> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // 여기 코드 넣어야됨

        final Context context = viewGroup.getContext();

        if(view == null)
            view = inflater.inflate(layout, viewGroup, false);

        FavoriteGridItem favoriteGridItem = data.get(position);

        TextView text_course = (TextView) view.findViewById(R.id.text_course);
        //ImageButton image_star = (ImageButton) view.findViewById(R.id.image_star);
        TextView text_subwayinfo = (TextView) view.findViewById(R.id.text_subwayinfo);
        TextView text_realtime = (TextView) view.findViewById(R.id.text_realtime);
        TextView text_bestseat = (TextView)  view.findViewById(R.id.text_bestseat);
        TextView text_fastseat = (TextView)  view.findViewById(R.id.text_fastseat);

        // 아이템 내 각 위젯에 데이터 반영
        text_course.setText(favoriteGridItem.getStation1() + " → " + favoriteGridItem.getStation2());
        //image_star.setImageResource();
        text_subwayinfo.setText(favoriteGridItem.getTrain() + "호선 " + favoriteGridItem.getDirection() + "행");
        //text_realtime.setText();
        text_bestseat.setText(favoriteGridItem.getCell_best());
        text_fastseat.setText(favoriteGridItem.getCell_fast());

        return view;
    }

}

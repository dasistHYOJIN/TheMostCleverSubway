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
import com.example.hyo_jin.themostcleversubway.Item.ResultItem;
import com.example.hyo_jin.themostcleversubway.Item.ResultItem1;
import com.example.hyo_jin.themostcleversubway.Item.ResultItem2;
import com.example.hyo_jin.themostcleversubway.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyo-Jin on 2018-06-06.
 */

public class TestAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layout;

    private List<ResultItem> items = new ArrayList<>();

    public TestAdapter(Context context, List<ResultItem> items) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        // getView가 생성하는 뷰의 개수를 리턴
        return 3;//return super.getViewTypeCount();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        int res = getItemViewType(position);

        // 최초 호출이면 항목 뷰를 생성
        if(view == null) {
            switch (res) {
                case 1 :
                    res = R.layout.listlayout_result1;
                    break;
                case 2 :
                    res = R.layout.listlayout_result2;
                    break;
                case 3 :
                    res = R.layout.listlayout_result3;

                    break;
            }

            view = inflater.inflate(res, viewGroup, false);
        }

        // 화면에 뿌린 뒤 여기서 각 항목에 해당하는 값을 바꿔주는 부분
        ResultItem item = items.get(position);

        switch (res) {
            case 1 :
                res = R.layout.listlayout_result1;

                //ImageButton btn_timetable = (ImageButton) view.findViewById(R.id.btn_timetable);
                TextView station_name = (TextView) view.findViewById(R.id.station_name);
                TextView direction = (TextView) view.findViewById(R.id.direction);
                TextView lefttime = (TextView) view.findViewById(R.id.lefttime);

                // 아이템 내 각 위젯에 데이터 반영
                station_name.setText(item.getStation());
                direction.setText(item.getDirection());
                lefttime.setText(item.getTime()[0] + " 후 도착\n"
                        + item.getTime()[1] + " 후 도착");

                break;
            case 2 :
                res = R.layout.listlayout_result2;

                TextView left = (TextView) view.findViewById(R.id.left);
                TextView bestseat = (TextView) view.findViewById(R.id.bestseat);
                TextView fastseat = (TextView) view.findViewById(R.id.fastseat);

                break;
            case 3 :
                res = R.layout.listlayout_result1;

                //ImageButton btn_timetable = (ImageButton) view.findViewById(R.id.btn_timetable);
                TextView station_name_last = (TextView) view.findViewById(R.id.station_name_last);
                TextView direction_last = (TextView) view.findViewById(R.id.direction_last);
                TextView lefttime_last = (TextView) view.findViewById(R.id.lefttime_last);

                break;
        }

        return view;
    }

}

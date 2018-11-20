package com.example.hyo_jin.themostcleversubway.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hyo_jin.themostcleversubway.Item.FavoriteGridItem;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyo-Jin on 2018-05-14.
 * 즐겨찾기 그리드 어댑터
 */

public class FavoriteGridAdapter extends BaseAdapter {

    String TAG = "FavoriteGridAdapter";

    private LayoutInflater inflater;
    private List<FavoriteGridItem> data = new ArrayList<FavoriteGridItem>();
    private int layout;
    //private int time;

    TextView text_course;
    TextView text_subwayinfo;
    TextView text_realtime;
    TextView text_bestseat;
    TextView text_fastseat;

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
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        Context context = viewGroup.getContext();

        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
            // init
            init(view);
        } else {
            // TODO
        }


        final FavoriteGridItem favoriteGridItem = data.get(position);


        // 아이템 내 각 위젯에 데이터 반영
        text_course.setText(favoriteGridItem.getStation1() + " → " + favoriteGridItem.getStation2());

        Log.v(TAG, favoriteGridItem.getStation1() + " " +favoriteGridItem.getStation2());
        Log.v(TAG, favoriteGridItem.toString());
        Log.v(TAG, "1");

        String arrCode = favoriteGridItem.getArrCode();
        switch (arrCode) {
            case "-1" :
            case "0" :
            case "1" :
            case "2" :
            case "3" :
            case "4" :
            case "5" :
                text_realtime.setTextSize(35f);
                break;
            default:
                text_realtime.setTextSize(40f);
                break;
        }

        text_subwayinfo.setText(favoriteGridItem.getLineNum() + " " + favoriteGridItem.getDirection());
        text_realtime.setText(favoriteGridItem.getRealtime());
        text_bestseat.setText(favoriteGridItem.getBestseat());
        text_fastseat.setText(favoriteGridItem.getFastseat());

        // 1초에 한번씩 갱신
        /*final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                time--;
                String realtime =  String.format("%02d", (time / 60)) + " : " + String.format("%02d", (time % 60));
                text_realtime.setText(realtime);
            }
        };
        timer.schedule(timerTask, 0, 1000);*/

        return view;
    }



    protected void init(View view) {
        text_course = (TextView) view.findViewById(R.id.text_course);
        text_course.setText("아무개역 → 어떤역");
        text_subwayinfo = (TextView) view.findViewById(R.id.text_subwayinfo);
        text_realtime = (TextView) view.findViewById(R.id.text_realtime);
        text_realtime.setText("서버에서 정보를 불러오고 있어요.");
        text_realtime.setTextSize(20f); //android:textSize="40sp"
        text_bestseat = (TextView)  view.findViewById(R.id.text_bestseat);
        text_fastseat = (TextView)  view.findViewById(R.id.text_fastseat);
    }

    // AsyncTask를 사용하여 데이터 호출
    class SubwayAsync extends AsyncTask<String, String, TextView> {

        @Override
        protected TextView doInBackground(String... params) {

        }
    }
}

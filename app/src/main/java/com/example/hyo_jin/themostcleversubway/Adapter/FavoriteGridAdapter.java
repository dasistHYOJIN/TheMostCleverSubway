package com.example.hyo_jin.themostcleversubway.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.Item.FavoriteGridItem;
import com.example.hyo_jin.themostcleversubway.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Hyo-Jin on 2018-05-14.
 * 즐겨찾기 그리드 어댑터
 */

public class FavoriteGridAdapter extends BaseAdapter {

    String TAG = "FavoriteGridAdapter";

    private LayoutInflater inflater;
    private List<FavoriteGridItem> data = new ArrayList<FavoriteGridItem>();
    private int layout;
    private JSONArray jsonArray;
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
        // 여기 코드 넣어야됨

        Context context = viewGroup.getContext();

        if(view == null)
            view = inflater.inflate(layout, viewGroup, false);

        final FavoriteGridItem favoriteGridItem = data.get(position);

        // init
        init(view);

        // 아이템 내 각 위젯에 데이터 반영
        text_course.setText(favoriteGridItem.getStation1() + " → " + favoriteGridItem.getStation2());

        // API 호출
        String url = "http://swopenapi.seoul.go.kr/api/subway/6e48535a78686765353479756b6b67/json/realtimeStationArrival/0/5/" + favoriteGridItem.getStation1();
        Log.v("에헴", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // 서버에 접속해서 json 데이터 중에 실데이터 배열만 받고
                        try {
                            jsonArray = response.getJSONArray("realtimeArrivalList"); // jsonArray에 저장

                            JSONObject jsonObject = new JSONObject();
                            // 상행/하행 구분하기 위해서
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String s = jsonArray.getJSONObject(i).getString("updnLine");
                                Log.v(TAG, jsonArray.getJSONObject(i).getString("statnNm") + " " +jsonArray.getJSONObject(i).getString("updnLine"));
                                if (s.equals(favoriteGridItem.getWay())) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    break;
                                }
                                else jsonObject= jsonArray.getJSONObject(0);
                            }
                            String trainDestiny = jsonObject.getString("trainLineNm").split("-")[0];
                            String realtime;
                            String arrCode = jsonObject.getString("arvlCd");
                            switch (arrCode) {
                                case "0" :
                                    realtime = "진입";
                                    break;
                                case "1" :
                                    realtime = "도착";
                                    break;
                                case "2" :
                                    realtime = "출발";
                                    break;
                                case "3" :
                                    realtime = "전역출발";
                                    break;
                                case "4" :
                                    realtime = "전역진입";
                                    break;
                                case "5" :
                                    realtime = "전역도착";
                                    break;
                                default:
                                    int time = jsonObject.getInt("barvlDt");
                                    realtime =  String.format("%02d", (time / 60)) + " : " + String.format("%02d", (time % 60));
                                    break;
                            }

                            text_subwayinfo.setText("3호선 " + trainDestiny);
                            text_realtime.setText(realtime);
                            text_bestseat.setText("6-4");
                            text_fastseat.setText("6-1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        request.setShouldCache(false);
        Volley.newRequestQueue(context).add(request);

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
        text_subwayinfo = (TextView) view.findViewById(R.id.text_subwayinfo);
        text_realtime = (TextView) view.findViewById(R.id.text_realtime);
        text_bestseat = (TextView)  view.findViewById(R.id.text_bestseat);
        text_fastseat = (TextView)  view.findViewById(R.id.text_fastseat);
    }
}

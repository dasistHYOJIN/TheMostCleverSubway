package com.example.hyo_jin.themostcleversubway.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hyo_jin.themostcleversubway.Item.FavoriteGridItem;
import com.example.hyo_jin.themostcleversubway.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Hyo-Jin on 2018-05-14.
 * 즐겨찾기 그리드 어댑터
 */

public class FavoriteGridAdapter extends BaseAdapter {

    String TAG = "FavoriteGridAdapter";

    protected SharedPreferences setting;
    protected SharedPreferences.Editor editor;

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
        setting = context.getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();

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
        if (view == null)
            view = inflater.inflate(layout, viewGroup, false);

        // init
        init(view);

        FavoriteGridItem favoriteGridItem = data.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        text_course.setText(favoriteGridItem.getStation1() + " → " + favoriteGridItem.getStation2());
        if (!favoriteGridItem.getStation1().equals("출발역"))
            new SubwayAsync(favoriteGridItem.getStation1(), favoriteGridItem.getWay()).execute();

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
        text_realtime.setText("서버에서 정보를 불러오고 있어요.");
        text_realtime.setTextSize(20f); //android:textSize="40sp"
        text_bestseat = (TextView)  view.findViewById(R.id.text_bestseat);
        text_fastseat = (TextView)  view.findViewById(R.id.text_fastseat);
    }

    // AsyncTask를 사용하여 데이터 호출
    // AsyncTask<doInBackground(), onProgressUpdate(), onPostExecute()>
    class SubwayAsync extends AsyncTask<Void, String, JSONObject> {
        private final String REQUEST_METHOD = "POST";
        private final int READ_TIMEOUT = 15000;
        private final int CONNECTION_TIMEOUT = 15000;

        private String station1, way;
        private String lineNum;
        private String direction;
        private String arrCode;
        private String realtime;
        private String bestseat = "0-0";
        private String fastseat = "0-0";

        public SubwayAsync(String station1, String way) {
            this.station1 = station1;
            this.way = way;
        }

        @Override
        // String... params => 같은 형태의 복수의 인자를 넣을 수 있으며, 함수로 들어오는 인자는 배열로 들어온다
        // 예를 들어 execute("A", "B", "C", ...)로 AsyncTask를 동작시킨다면, doInBackground의 String...은 배열로 들어옴다는 것
        protected JSONObject doInBackground(Void... params) {
            JSONObject result = null;

            try {
                URL url = new URL("http://54.180.32.17:3000/add/favorite/full/get/" + station1 + "/" + way);//"http://localhost:3000/add/favorite/full/get/" + station1 + "/" + way;
                String url_params = "token=" + setting.getString("token", null);

                //Create a connection
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                // 파라미터 전달 및 데이터 읽어오기
                OutputStream os = connection.getOutputStream();
                os.write(url_params.getBytes("UTF-8")); // 출력 스트림에 출력
                os.flush(); // 출력 스트림을 비우고 버퍼링 된 모든 출력 바이트를 강제 실행
                os.close();

                //Connect to url
                connection.connect();

                InputStreamReader is = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(is);
                StringBuilder sb = new StringBuilder();

                sb.append(br.readLine());
                br.close();
                is.close();

                Log.v(TAG, "doInBackground >> " + station1 + " " + sb.toString());
                result = new JSONObject(sb.toString());
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (jsonObject == null) return;

            Log.v(TAG,  "onPostExecute >> " + station1 + " " + jsonObject);
            // json 데이터 중에 실데이터 배열만 받고
            try {
                // 상행/하행 구분하기 위해서
                lineNum = jsonObject.getString("lineNum");
                direction = jsonObject.getString("direction");
                arrCode = jsonObject.getString("arvlCd");

                switch (arrCode) {
                    case "-1" :
                        text_realtime.setTextSize(35f);
                        realtime = "응답오류";
                        break;
                    case "0" :
                        text_realtime.setTextSize(35f);
                        realtime = "진입";
                        break;
                    case "1" :
                        text_realtime.setTextSize(35f);
                        realtime = "도착";
                        break;
                    case "2" :
                        text_realtime.setTextSize(35f);
                        realtime = "출발";
                        break;
                    case "3" :
                        text_realtime.setTextSize(35f);
                        realtime = "전역출발";
                        break;
                    case "4" :
                        text_realtime.setTextSize(35f);
                        realtime = "전역진입";
                        break;
                    case "5" :
                        text_realtime.setTextSize(35f);
                        realtime = "전역도착";
                        break;
                    default:
                        text_realtime.setTextSize(40f);
                        int time = jsonObject.getInt("realtime");
                        realtime =  String.format("%02d", (time / 60)) + " : " + String.format("%02d", (time % 60));
                        break;
                }

                text_subwayinfo.setText(lineNum + " " + direction);
                text_realtime.setText(realtime);
                text_bestseat.setText(bestseat);
                text_fastseat.setText(fastseat);

                Log.v(TAG, station1 + " >> " + text_realtime.getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
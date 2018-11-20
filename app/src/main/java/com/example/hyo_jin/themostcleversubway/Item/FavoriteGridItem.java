package com.example.hyo_jin.themostcleversubway.Item;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Hyo-Jin on 2018-05-14.
 */

public class FavoriteGridItem {

    String TAG = "FavoriteGridItem";

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    private RequestQueue queue;
    private String fav_id;
    private String station1;
    private String station2;
    private String way;
    private String lineNum;
    private String direction;
    private String arrCode = "";
    private String realtime = "";
    private String bestseat = "";
    private String fastseat = "";

    public FavoriteGridItem(Context context, RequestQueue queue, String id, String station1, String station2, String way) {
        setting = context.getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();

        this.queue = queue;

        this.fav_id = id;
        this.station1 = station1;
        this.station2 = station2;
        this.way = way;
        callApi();
        Log.v(TAG, this.toString());
    }

    // input: station1, station2, way
    // return lineNum, direction, arvlCd, realtime
    protected void callApi() {
        // API 호출
        String url = "http://54.180.32.17:3000/add/favorite/full/get/" + station1 + "/" + way;//"http://swopenapi.seoul.go.kr/api/subway/6e48535a78686765353479756b6b67/json/realtimeStationArrival/0/5/" + favoriteGridItem.getStation1();

        JSONObject params = new JSONObject();
        try {
            params.put("token", setting.getString("token", null));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, "response " + response);
                        // 서버에 접속해서 json 데이터 중에 실데이터 배열만 받고
                        try {
                            JSONObject jsonObject = response;
                            // 상행/하행 구분하기 위해서
                            lineNum = jsonObject.getString("lineNum");
                            direction = jsonObject.getString("direction");
                            arrCode = jsonObject.getString("arvlCd");
                            Log.v(TAG, station1 + " >> " + jsonObject.getString("lineNum"));
                            Log.v(TAG, station1 + " >> " + jsonObject.getString("direction"));
                            Log.v(TAG, station1 + " >> " + jsonObject.getString("arvlCd"));

                            switch (arrCode) {
                                case "-1" :
                                    realtime = "응답오류";
                                    break;
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
                                    int time = jsonObject.getInt("realtime");
                                    realtime =  String.format("%02d", (time / 60)) + " : " + String.format("%02d", (time % 60));
                                    break;
                            }
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
        queue.add(request);
    }

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    public String getStation1() {
        return station1;
    }

    public void setStation1(String station1) {
        this.station1 = station1;
    }

    public String getStation2() {
        return station2;
    }

    public void setStation2(String station2) {
        this.station2 = station2;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getArrCode() {
        return arrCode;
    }

    public void setArrCode(String arrCode) {
        this.arrCode = arrCode;
    }

    public String getRealtime() {
        return realtime;
    }

    public void setRealtime(String realtime) {
        this.realtime = "4-3";
    }

    public String getBestseat() {
        return bestseat;
    }

    public void setBestseat(String bestseat) {
        this.bestseat = bestseat;
    }

    public String getFastseat() {
        return fastseat;
    }

    public void setFastseat(String fastseat) {
        this.fastseat = "6-1";
    }

    @Override
    public String toString() {
        return "FavoriteGridItem{" +
                "fav_id='" + fav_id + '\'' +
                ", station1='" + station1 + '\'' +
                ", station2='" + station2 + '\'' +
                ", way='" + way + '\'' +
                ", lineNum='" + lineNum + '\'' +
                ", direction='" + direction + '\'' +
                ", arrCode='" + arrCode + '\'' +
                ", realtime='" + realtime + '\'' +
                ", bestseat='" + bestseat + '\'' +
                ", fastseat='" + fastseat + '\'' +
                '}';
    }
}

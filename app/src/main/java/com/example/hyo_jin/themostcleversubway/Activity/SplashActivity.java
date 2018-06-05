package com.example.hyo_jin.themostcleversubway.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.DB.StationDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    private JSONArray jsonArray; // 지하철 json

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 스플래시 화면은 뷰를 inflate 하지 않음 : setContentView(R.layout.activity_splash);

        getSubwayData(); // 서버에 접속해서 json 데이터 가져오기
        Log.v(TAG, "JSON 데이터 DB에 넣음!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected void getSubwayData() {
        Log.v(TAG, "getSubwayData() 실행");

        /*** 서버 연결해서 데이터 받기 ***/
        String url = "http://192.168.0.33:3000"; // 집IP
        //String url = "http://192.168.43.1:3000";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // 서버에 접속해서 json 데이터 중에 실데이터 배열만 받고
                        try {
                            Log.v(TAG, "서버에 접속됨!");
                            jsonArray = response.getJSONArray("DATA"); // jsonArray에 저장

                            Log.v(TAG, "JSON 데이터 이제 DB에 넣을거야");
                            //insertDataInDB(); // DB에 데이터 넣기
                            StationDBHelper dbHelper = new StationDBHelper(getApplicationContext(), jsonArray);
                            SQLiteDatabase database = dbHelper.getWritableDatabase();
                            Log.v(TAG, "JSON 데이터 들어갔나");
                        } catch (JSONException e) {
                            Log.v(TAG, "서버 연결 안됨!");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Log.v(TAG, "웹 서버 데이터요청 실패");
            }
        });

        request.setShouldCache(false);
        Volley.newRequestQueue(this).add(request);
    }

/*    protected void insertDataInDB() {
        Log.v(TAG, "insertDataInDB() 실행");
        StationDBHelper dbHelper = new StationDBHelper(this, jsonArray);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            // JsonObjectRequest에서 데이터를 못받아왔을 떄
            if (jsonArray == null) {
                Log.v(TAG, "JsonObjectRequest에서 데이터를 못받아옴");
                return;
            }

            // JSONArray 클래스는 JSON 파일에서 배열을 읽어들인다.
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject station = jsonArray.getJSONObject(i);

                dbHelper.insert(database, station.getInt("station_cd"), station.getString("line_num"),
                        station.getString("station_nm"), station.getString("fr_code"));
            }
            Log.v(TAG, "JSON 배열 읽어옴");
            dbHelper.getReadableDatabase();
            dbHelper.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}

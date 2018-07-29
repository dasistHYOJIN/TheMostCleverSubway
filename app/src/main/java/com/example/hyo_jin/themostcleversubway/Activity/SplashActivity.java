package com.example.hyo_jin.themostcleversubway.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.DB.StationDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    private JSONArray jsonArray; // 지하철 json

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 스플래시 화면은 뷰를 inflate 하지 않음 : setContentView(R.layout.activity_splash);

        getSubwayData(); // 서버에 접속해서 json 데이터 가져오기
        getKeyData();
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
        String url = "http://192.168.0.33:3000/filedownload/lineinfo"; // 집IP

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // 서버에 접속해서 json 데이터 중에 실데이터 배열만 받고
                        try {
                            Log.v(TAG, "서버에 접속됨!");
                            jsonArray = response.getJSONArray("DATA"); // jsonArray에 저장

                            Log.v(TAG, "JSON 데이터 이제 DB에 넣을거야");
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

    protected void getKeyData() {
        // TODO: 사용자 정보를 넣으면 DB에 존재하는지 확인한 후에 apiKey 전달
        // TODO: 전달받은 키는 sharedPreference에 넣기
        Log.v(TAG, "getkeyData() 실행");

        /*** 서버 연결해서 데이터 받기 ***/
        String url = "http://192.168.0.33:3000/keydownload/seoulKey";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String key = response;
                        Log.v(TAG, key);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "웹 서버 서울시 apiKey 데이터요청 실패");
            }
        });

        request.setShouldCache(false);
        Volley.newRequestQueue(this).add(request);
    }


}

package com.example.hyo_jin.themostcleversubway.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.DB.StationDBHelper;
import com.example.hyo_jin.themostcleversubway.Model.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private static final String HOST = "http://54.180.32.17:3000";

    private JSONArray jsonArray; // 지하철 json

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // sharedPreference
        SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);
        final SharedPreferences.Editor editor = setting.edit();

        final UserInfo userInfo = getUserInfo();

        // 최초 실행 시
        if (setting.getString("token", null) == null) {
            // UserInfo 객체에다가 사용자 정보넣어서 서버에다가 보내
            // 서버에서 DB에다 정보를 넣으면 개인 해시id를 클라이언트에 보내줌
            // 나중에 보내줄때

            String url = HOST + "/user/register";

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            Log.v(TAG, "onResponse "+response);

                            // 서버에서 개인 토큰값 받으면 SharedPreferences에 저장
                            editor.putString("token", response);
                            editor.commit();

                            // 공공데이터 API Key 가져오는 함수 호출
                            getKeyData(response);
                            // 지하철역 데이터 가져오는 함수 호출
                            getSubwayData();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("userid", userInfo.getUserid());
                    params.put("osVersion", userInfo.getOsVersion());
                    params.put("model", userInfo.getModel());
                    params.put("display", userInfo.getDisplay());
                    params.put("manufacturer", userInfo.getManufacturer());

                    return params;
                }
            };
            request.setShouldCache(false);
            Volley.newRequestQueue(this).add(request);

        } else { // 이미 등록돼있는 사용자일 경우
            // 공공데이터 API Key 가져오는 함수 호출
            getKeyData(setting.getString("token", null));
        }

        Log.v(TAG, "token " + setting.getString("token", null));

        // 스플래시 화면은 뷰를 inflate 하지 않음 : setContentView(R.layout.activity_splash);

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
        String url = HOST + "/filedownload/lineinfo"; // 집IP

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

    protected void getKeyData(final String userKey) {
        // TODO: 사용자 정보를 넣으면 DB에 존재하는지 확인한 후에 apiKey 전달
        // TODO: 전달받은 키는 sharedPreference에 넣기
        Log.v(TAG, "getkeyData() 실행");

        final String token = userKey;

        if (userKey == null) {
            // TODO: 존재하지 않는 사용자라고 하면서 꺼지거나 사용자 재등록하거나
        }

        /*** 서버 연결해서 데이터 받기 ***/
        String url = HOST + "/key/seoulKey";

        StringRequest request = new StringRequest(Request.Method.POST, url,
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);

                return params;
            }
        };

        request.setShouldCache(false);
        Volley.newRequestQueue(this).add(request);
    }

    public UserInfo getUserInfo() {

        UserInfo userInfo;

        String userid = getUserId();
        String osVerision = Build.VERSION.RELEASE;
        String model = Build.MODEL;
        String display = getDisplay(this);
        String manufacturer = Build.MANUFACTURER;

        userInfo = new UserInfo(userid, osVerision, model, display, manufacturer);

        return userInfo;
    }

    private static String getUserId() {
        TimeZone tz;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");

        tz = TimeZone.getTimeZone("Asia/Seoul");
        simpleDateFormat.setTimeZone(tz);
        simpleDateFormat.format(date);

        int random = new Random().nextInt(24)+65;

        return simpleDateFormat.format(date) + (char)random;
    }

    private static String getDisplay(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;

        return deviceWidth + "*" + deviceHeight;
    }
}

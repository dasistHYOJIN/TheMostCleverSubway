package com.example.hyo_jin.themostcleversubway.Activity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.Adapter.ResultPagerAdapter;
import com.example.hyo_jin.themostcleversubway.Adapter.SectionsPagerAdapter;
import com.example.hyo_jin.themostcleversubway.DeviceInfo;
import com.example.hyo_jin.themostcleversubway.Fragments.FavoriteFragment;
import com.example.hyo_jin.themostcleversubway.Fragments.HistoryFragment;
import com.example.hyo_jin.themostcleversubway.Fragments.ResultFragment;
import com.example.hyo_jin.themostcleversubway.Fragments.SearchFragment;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private final String TAG = "ResultActivity";

    private ResultPagerAdapter mResultPagerAdapter;
    private ViewPager mViewPager;

    /** 플젝용 (졸프ㄴㄴ) **/
    private EditText edit_addr, edit_add;
    private Button btn_sbm, btn_add;
    private TextView text_result;
    /** 플젝용 (졸프ㄴㄴ) **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);
        init();

        /** 플젝용 **/

        btn_sbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = edit_addr.getText().toString();

                // StringRequest : 서버에 데이터를 요청한 후 문자열 타입으로 된 응답을 받음
                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                Log.v(TAG, "웹 서버 데이터요청 응답!");
                                text_result.append("웹 서버 데이터요청 응답!\n");
                                Log.v(TAG, "결과는 : " + response + "\n");
                                text_result.append("결과는 : " + response + "\n");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();

                                Log.v(TAG, "웹 서버 추가 데이터요청 실패여");
                                text_result.append("웹 서버 추가 데이터요청 실패여\n");
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        DeviceInfo deviceInfo = getDeviceInfo();
                        params.put("mobile", deviceInfo.getMobile());
                        params.put("osVersion", deviceInfo.getOsVersion());
                        params.put("model", deviceInfo.getModel());
                        params.put("display", deviceInfo.getDisplay());
                        params.put("manufacturer", deviceInfo.getManufacture());
                        params.put("macAddress", deviceInfo.getMacAddress());

                        return super.getParams();
                    }
                };

                request.setShouldCache(false);
                Volley.newRequestQueue(getApplicationContext()).add(request);

                Log.v(TAG, "웹 서버에 추가 요청함 : " + url);
                text_result.append("웹 서버에 추가 요청함 : " + url + "\n");
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = edit_add.getText().toString();

                Log.v(TAG, "웹 서버 추가 URL : " + url + "\n" );

                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                Log.v(TAG, "웹 서버 추가 데이터요청 응답!");
                                text_result.append("웹 서버 추가 데이터요청 응답!\n");
                                Log.v(TAG, "추가 결과는 : " + response + "\n");
                                text_result.append("추가 결과는 : " + response + "\n");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();

                                Log.v(TAG, "웹 서버 추가 데이터요청 실패여");
                                text_result.append("웹 서버 추가 데이터요청 실패여\n");
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        DeviceInfo deviceInfo = getDeviceInfo();
                        params.put("mobile", deviceInfo.getMobile());
                        params.put("osVersion", deviceInfo.getOsVersion());
                        params.put("model", deviceInfo.getModel());
                        params.put("display", deviceInfo.getDisplay());
                        params.put("manufacturer", deviceInfo.getManufacture());
                        params.put("macAddress", deviceInfo.getMacAddress());

                        return super.getParams();
                    }
                };

                request.setShouldCache(false);
                Volley.newRequestQueue(getApplicationContext()).add(request);

                Log.v(TAG, "웹 서버에 추가 요청함 : " + url);
                text_result.append("웹 서버에 추가 요청함 : " + url + "\n");
            }
        });


        /** 플젝용 **/

        mResultPagerAdapter = new ResultPagerAdapter(getSupportFragmentManager());

        setupViewPager(mViewPager);

        //Toast.makeText(this, "Testing result activity ", Toast.LENGTH_SHORT).show();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupViewPager(ViewPager viewPager) {
        ResultPagerAdapter adapter = new ResultPagerAdapter(getSupportFragmentManager(), new ResultFragment());

//        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    protected void init() {
        mViewPager = (ViewPager) findViewById(R.id.container);

        // 플젝용 (졸프ㄴㄴ)
        edit_addr = (EditText) findViewById(R.id.edit_addr);
        btn_sbm = (Button) findViewById(R.id.btn_sbm);
        edit_add = (EditText) findViewById(R.id.edit_add);
        btn_add = (Button) findViewById(R.id.btn_add);
        text_result = (TextView) findViewById(R.id.text_result);
        text_result.setText("");

    }

    /** 플젝용 **/
    public DeviceInfo getDeviceInfo() {
        Log.v(TAG, "엣헴1 ");
        DeviceInfo deviceInfo = null;

        Log.v(TAG, "엣헴2 ");
        String mobile = null;

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        Log.v(TAG, "엣헴3 " + telephonyManager);

        /*if(telephonyManager.getLine1Number() != null) {
            Log.v(TAG, "엣헴4 ");
            mobile = telephonyManager.getLine1Number();
        }*/

        try {
            Log.v(TAG, "엣헴4.1 ");
            mobile = telephonyManager.getLine1Number();
            Log.v(TAG, "엣헴4.2 ");
        } catch(SecurityException e) {
            Log.v(TAG, "엣헴4.3 ");
            e.printStackTrace();
        }
        //mobile = "010-9993-1114";
        Log.v(TAG, "엣헴5 ");

        String osVersion = Build.VERSION.RELEASE;
        String model = Build.MODEL;
        String display = getDisplay(this);
        String manufacture = Build.MANUFACTURER;
        String macAddress = getMacAddress(this);

        deviceInfo = new DeviceInfo(mobile, osVersion, model, display, manufacture, macAddress);

        Log.v(TAG, "엣헴5 " + deviceInfo.getOsVersion());


        return deviceInfo;
    }

    public String getDisplay(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;

        return deviceWidth + "x" + deviceHeight;
    }

    private String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        return wifiInfo.getMacAddress();
    }
    /** 플젝용 **/
}


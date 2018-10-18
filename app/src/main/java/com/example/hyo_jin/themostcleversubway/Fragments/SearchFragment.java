package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.Activity.ResultActivity;
import com.example.hyo_jin.themostcleversubway.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

/**
 * selectDataInDB() : DB에서 데이터 가져와서 리스트에 추가하기
 * init() : 컴포넌트 초기화
 * 프래그먼트 > 액티비티 > 프래그먼트 : https://developer.android.com/training/basics/fragments/communicating?hl=ko
 */
public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private static final String HOST = "http://54.180.32.17:3000";

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    private EditText edit_dep, edit_arr;
    private ImageButton btn_replace, btn_search;
    private Button btn_map, btn_abc, btn_line;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        init(view);

        setting = getActivity().getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();

        /**** 검색 조건 리스트 프래그먼트에 외부(?) 프래그먼트 import하기 ****/
        // Create new fragment and transaction
        Fragment fragment = new SubSearch1Fragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        fragmentTransaction.add(R.id.list_fragment, fragment);

        // Commit the transaction
        fragmentTransaction.commit();

        btn_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOnClick(view);
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOnClick(view);
            }
        });
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFragmentOnClick(view);
            }
        });
        btn_abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFragmentOnClick(view);
            }
        });
        btn_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFragmentOnClick(view);
            }
        });

        return view;
    }

    public void onStationClick(String station) {
        ((EditText) getActivity().getCurrentFocus()).setText(station);
    }

    /* 프래그먼트 선택 버튼 눌렀을 때 */
    public void myFragmentOnClick(View v) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        switch (v.getId()) {
            case R.id.btn_map :
                Fragment fragment1 = new SubSearch1Fragment();
                fragmentTransaction.remove(getFragmentManager().findFragmentById(R.id.list_fragment)).replace(R.id.list_fragment, fragment1);

                break;
            case R.id.btn_abc :
                Fragment fragment2 = new SubSearch2Fragment();
                fragmentTransaction.remove(getFragmentManager().findFragmentById(R.id.list_fragment)).replace(R.id.list_fragment, fragment2);

                break;
            case R.id.btn_line :
                Fragment fragment3 = new SubSearch3Fragment();
                fragmentTransaction.remove(getFragmentManager().findFragmentById(R.id.list_fragment)).replace(R.id.list_fragment, fragment3);

                break;
        }

        fragmentTransaction.commit();
    }

    public void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_replace :
                swap_station();

                break;
            case R.id.btn_search : // 검색 버튼 리스너
                // 출발역, 도착역 받아와서
                String depart = edit_dep.getText().toString();
                String arrive = edit_arr.getText().toString();

                // 검색기록 추가 함수
                Log.v(TAG, setting.getString("token", null));
                add_history(depart, arrive);

                Intent intent = new Intent(SearchFragment.super.getContext(), ResultActivity.class);
                intent.putExtra("station_dep", depart);
                intent.putExtra("station_arr", arrive);
                startActivity(intent);

                // ResultFragment가 이미 떠있으면 실행ㄴㄴ
                /*if (getFragmentManager().findFragmentById(R.id.list_fragment).getClass() != ResultFragment.class) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.list_fragment, new ResultFragment(depart, arrive));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }*/
                break;
            default:
                break;
        }
    }

    private String getHistoryId() {
        TimeZone tz;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmm");

        tz = TimeZone.getTimeZone("Asia/Seoul");
        simpleDateFormat.setTimeZone(tz);
        simpleDateFormat.format(date);

        int random = new Random().nextInt(24)+65;

        return "HT" + simpleDateFormat.format(date) + (char)random;
    }

    private void add_history(String depart, String arrive) {

        String url = HOST + "/add/history/" + depart +"/" + arrive;
        Log.v(TAG, "url "+url);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "onResponse "+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "error "+error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", setting.getString("token", null));
                params.put("id", getHistoryId());

                return params;
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(request);

    }

    // btn_replace 누르면 출발역 도착역 바꾸기
    protected void swap_station() {
        String station1 = edit_dep.getText().toString();
        String station2 = edit_arr.getText().toString();

        edit_arr.setText(station1);
        edit_dep.setText(station2);
    }

    // 컴포넌트 초기화
    protected void init(View view) {

        edit_dep = (EditText) view.findViewById(R.id.edit_dep);
        edit_arr = (EditText) view.findViewById(R.id.edit_arr);
        // 클릭해도 키보드 안나오게
        edit_dep.setInputType(0);
        edit_arr.setInputType(0);

        btn_replace = (ImageButton) view.findViewById(R.id.btn_replace);
        btn_search = (ImageButton) view.findViewById(R.id.btn_search);

        btn_map = (Button) view.findViewById(R.id.btn_map);
        btn_abc = (Button) view.findViewById(R.id.btn_abc);
        btn_line = (Button) view.findViewById(R.id.btn_line);
        btn_map.setText("노선도");
        btn_abc.setText("가나다");
        btn_line.setText("호선순");
    }
}

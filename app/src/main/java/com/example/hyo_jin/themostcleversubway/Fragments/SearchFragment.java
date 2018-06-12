package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * selectDataInDB() : DB에서 데이터 가져와서 리스트에 추가하기
 * init() : 컴포넌트 초기화
 */
public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";

    private EditText edit_dep, edit_arr;
    private ImageButton btn_replace, btn_search;
    private Button btn_map, btn_abc, btn_line;

    private JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        init(view);


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

    public void onToast(String station) {
        Log.v(TAG, "0 : " + station);
        Toast.makeText(getContext(), "클릭한 지하철역 이름은 " + station, Toast.LENGTH_SHORT).show();
        Log.v(TAG, "1 : " + station);
        edit_arr.setText(station);
        Log.v(TAG, "2 : " + station);
        edit_dep.setText(station);
        Log.v(TAG, "3 : " + station);
    }

    /* 프래그먼트 선택 버튼 눌렀을 때 */
    public void myFragmentOnClick(View v) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        switch (v.getId()) {
            case R.id.btn_map :
                Fragment fragment1 = new SubSearch1Fragment();
                fragmentTransaction.replace(R.id.list_fragment, fragment1);

                break;
            case R.id.btn_abc :
                Fragment fragment2 = new SubSearch2Fragment();
                fragmentTransaction.replace(R.id.list_fragment, fragment2);

                break;
            case R.id.btn_line :
                Fragment fragment3 = new SubSearch3Fragment();
                fragmentTransaction.replace(R.id.list_fragment, fragment3);

                break;

        }

        //fragmentTransaction.addToBackStack(null);
        // Commit the fragment transaction
        fragmentTransaction.commit();
    }

    public void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_replace :
                swap_station();
                /*Toast.makeText(getActivity(), "시간표 액티비티를 띄워봄", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SearchFragment.super.getContext(), TimetablePopup.class));*/

                break;
            case R.id.btn_search : // 검색 버튼 리스너
                String depart = edit_dep.getText().toString();
                String arrive = edit_arr.getText().toString();

                String api_url = "http://swopenapi.seoul.go.kr/api/subway/apikey/"
                        + "json/shortestRoute/0/2/" + depart + "/" + arrive;
                Log.v(TAG, api_url);

                // 왜 얘는 GET이야
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, api_url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.v(TAG, "API 접속!");

                                try {
                                    jsonArray = response.getJSONArray("shortestRouteList"); // jsonArray에 저장
                                    Log.v(TAG, "JSON 데이터 받음!");

                                    Log.v(TAG, jsonArray.getJSONObject(0).getString("shtTravelMsg"));
                                    Toast.makeText(getActivity(), jsonArray.getJSONObject(0).getString("shtTravelMsg"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
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
                Volley.newRequestQueue(getContext()).add(request);

                break;
            default:
                break;
        }
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

        edit_arr = (EditText) view.findViewById(R.id.edit_arr);
        edit_dep = (EditText) view.findViewById(R.id.edit_dep);

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

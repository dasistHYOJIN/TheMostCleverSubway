package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.Activity.ResultActivity;
import com.example.hyo_jin.themostcleversubway.Activity.TimetablePopup;
import com.example.hyo_jin.themostcleversubway.DB.StationDBHelper;
import com.example.hyo_jin.themostcleversubway.Item.FavoriteGridItem;
import com.example.hyo_jin.themostcleversubway.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * selectDataInDB() : DB에서 데이터 가져와서 리스트에 추가하기
 * init() : 컴포넌트 초기화
 */
public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";

    private EditText edit_dep, edit_arr;
    private ImageButton btn_replace, btn_search;
    private Button btn_map, btn_abc, btn_line;
    private ListView list_lineNum, list_station;

    private JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        init(view);

        // 지하철역 초기는 1호선으로
        selectDataInDB("1"); // 데이터베이스에서 지하철역 정보 가져오기

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

        list_lineNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                myOnItemClick(adapterView, view, position, id);
            }
        });

        list_station.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                myOnItemClick(adapterView, view, position, id);
            }
        });

        return view;
    }

    public void myOnItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (adapterView.getId()) {
            case R.id.list_lineNum :
                String lineNum = adapterView.getItemAtPosition(position).toString();
                StationDBHelper dbHelper = new StationDBHelper(getActivity(), null);

                setStationList(dbHelper, lineNum);

                break;
            case R.id.list_station :
                String station = adapterView.getItemAtPosition(position).toString();

                edit_dep.setText(station);

                break;
            default:
                 break;

        }
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
            case R.id.list_lineNum :
                break;
            case R.id.list_station :
                break;
            default:
                break;
        }
    }

    /*** DB에서 데이터 가져와서 리스트에 추가하기 ***/
    protected void selectDataInDB(String lineNum) {
        StationDBHelper dbHelper = new StationDBHelper(getActivity(), null);

        setLineList(dbHelper);
        setStationList(dbHelper, lineNum);

        dbHelper.close();
    }

    public void setLineList(StationDBHelper dbHelper) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<String> linenum = new ArrayList<>(); // 선택기준 리스트뷰 리스트

        Cursor result = dbHelper.selectLeftList(database, false);
        // 결과 커서를 맨 처음으로 가게 함 -> false일 때는 result(Cursor 객체)가 비어 있는 경우임
        if(result.moveToFirst()){
            while (!result.isAfterLast()) {
                String line_num = result.getString(0);

                linenum.add(line_num);
                result.moveToNext();
            }
            result.close();
        }

        // 리스트뷰에 리스트 추가
        ArrayAdapter<String> adapter_linenum = new ArrayAdapter<String>(getContext(), R.layout.listlayout_linenum, R.id.text_subwayvalue, linenum);
        list_lineNum.setAdapter(adapter_linenum);
    }

    public void setStationList(StationDBHelper dbHelper, String lineNum) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<String> stationname = new ArrayList<>(); // 지하철역 리스트뷰 리스트

        Cursor result = dbHelper.selectRightList(database, lineNum, false);
        if(result.moveToFirst()){
            while (!result.isAfterLast()) {
                String station_name = result.getString(1);

                stationname.add(station_name);
                result.moveToNext();
            }
            result.close();
        }
        ArrayAdapter<String> adapter_linename = new ArrayAdapter<String>(getContext(), R.layout.listlayout_linenum, R.id.text_subwayvalue, stationname);
        list_station.setAdapter(adapter_linename);
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

        list_lineNum = (ListView) view.findViewById(R.id.list_lineNum);
        list_station = (ListView) view.findViewById(R.id.list_station);

    }
}

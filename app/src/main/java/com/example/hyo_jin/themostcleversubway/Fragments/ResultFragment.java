package com.example.hyo_jin.themostcleversubway.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.Adapter.RecyclerviewAdapter;
import com.example.hyo_jin.themostcleversubway.Item.ResultItem;
import com.example.hyo_jin.themostcleversubway.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 에러사항 : 검색결과 프래그먼트 띄운 상태에서 다른 탭 갔다가와서 뒤로가기하면 꺼짐
 */
public class ResultFragment extends Fragment {

    final String TAG = "ResultFragment";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //private List<ResultItem> result = new ArrayList<>();
    private JSONArray jsonArray;

    private String depart, arrive;

    public ResultFragment() {
    }

    public ResultFragment(String station1, String station2) {
        this.depart = station1;
        this.arrive = station2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        init(view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        if (depart != null && arrive != null)
            setResultJSON(depart, arrive);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public void setList() {

        String[] lefttime = {"3분 12초", "6분 9초"};

        List<ResultItem> result = new ArrayList<>();

        try {
            JSONObject object = jsonArray.getJSONObject(0);
            String statnIds[] = object.getString("shtStatnId").split(",");
            String statnNms[] = object.getString("shtStatnNm").split(",");
            String directions[] = object.getString("DIRECTION").split(",");

            /********************/
            String arrCode = object.getString("arvlCd");
            String realtime;

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
                    int time = object.getInt("realtime");
                    realtime =  (time / 60) + "분 " + String.format("%02d", (time % 60)) + "초";
                    break;
            }
            /********************/


            String statnId = statnIds[0];
            int transCnt = 0;
            result.add(new ResultItem(1, statnNms[0], directions[transCnt++] + "행", realtime, -1, statnId.substring(0, 4))); // 출발역 호선id
            for (int i = 0, count = 0; i < statnIds.length; i++) {
                Log.v(TAG, statnNms[i].trim() + " " + statnIds[i].substring(0, 4));
                if (statnIds[i].startsWith(statnId.substring(0, 4))) {
                    // 환승안할때
                    count++;
                    Log.v(TAG, "count " + count);
                } else {
                    // 환승할 때
                    result.add(new ResultItem(2, count, "2-6", "8-4"));
                    result.add(new ResultItem(1, statnNms[i], directions[transCnt++] + "행", realtime, -1, statnIds[i].substring(0, 4)));

                    statnId = statnIds[i];
                    count = 0;
                }

                // 마지막역일 때
                if (i == statnIds.length-1) {
                    result.add(new ResultItem(2, count, "2-6", "8-4"));
                    result.add(new ResultItem(3, statnNms[i], "", realtime, -1, statnIds[i].substring(0, 4)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // specify an adapter (see also next example)
        adapter = new RecyclerviewAdapter(result);
        recyclerView.setAdapter(adapter);
    }

    public void setResultJSON(String depart, String arrive) {
        String api_url = "http://54.180.32.17:3000/search/route/" + depart + "/" + arrive;
        Log.v(TAG, api_url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, api_url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, "API 접속!");

                        try {
                            jsonArray = response.getJSONArray("shortestRouteList"); // jsonArray에 저장
                            Log.v(TAG, "JSON 데이터 받음!");

                            Log.v(TAG, jsonArray.getJSONObject(0).getString("shtTravelMsg"));
                            setList();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Toast.makeText(getContext(), "서버와의 연결이 불안정해요!", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "웹 서버 데이터요청 실패");
            }
        });

        request.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(request);
    }

    protected void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
    }
}

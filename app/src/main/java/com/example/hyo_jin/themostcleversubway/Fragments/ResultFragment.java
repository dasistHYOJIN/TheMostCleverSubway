package com.example.hyo_jin.themostcleversubway.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.Adapter.TestAdapter;
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

    private ListView listview_test;
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

        Log.v(TAG, "짠");

        setResultJSON(depart, arrive);

        return view;
    }

    public void setList() {

        String[] lefttime = {"3분 12초", "6분 9초"};

        List<ResultItem> result = new ArrayList<>();

        try {
            JSONObject object = jsonArray.getJSONObject(0);
            String statnIds[] = object.getString("shtStatnId").split(",");
            String statnNms[] = object.getString("shtStatnNm").split(",");

            String statnId = statnIds[0];
            result.add(new ResultItem(1, statnNms[0], "", lefttime, -1, null, null));
            for (int i = 0, count = 0; i < statnIds.length; i++) {
                Log.v(TAG, statnNms[i].trim() + " " + statnIds[i].substring(0, 4));
                if (statnIds[i].startsWith(statnId.substring(0, 4))) {
                    // 환승안할때
                    count++;
                    Log.v(TAG, "count " + count);
                } else {
                    // 환승할 때
                    result.add(new ResultItem(2, null, null, null, count, "2-6", "8-4"));
                    result.add(new ResultItem(1, statnNms[i], "", lefttime, -1, null, null));

                    statnId = statnIds[i];
                    count = 0;
                }

                // 마지막역일 때
                if (i == statnIds.length-1) {
                    result.add(new ResultItem(2, null, null, null, count, "2-6", "8-4"));
                    result.add(new ResultItem(3, statnNms[i], "", lefttime, -1, null, null));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TestAdapter adapter = new TestAdapter(getContext(), result);
        listview_test.setAdapter(adapter);
    }

    public void setResultJSON(String depart, String arrive) {
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
        listview_test = (ListView) view.findViewById(R.id.listview_test);
    }


}

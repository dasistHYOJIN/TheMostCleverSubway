package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.Adapter.HistoryExpandableListAdapter;
import com.example.hyo_jin.themostcleversubway.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class HistoryFragment extends Fragment {
    private static final String TAG = "HistoryFragment";
    private static final String HOST = "http://54.180.32.17:3000";

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    private View result;
    private ExpandableListView expandableListView;
    //private ImageButton btn_timetable;
    private HistoryExpandableListAdapter adapter;
    private JSONArray jsonArray; // 지하철 json
    private List<String> groupData;
    private String[][] route = new String[][] {
            {"A"},
            {"B"},
            {"C"}
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        init(view);

        setting = getActivity().getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();

        Log.v(TAG, setting.getString("token", null));
        //get_history();
        String url = HOST + "/add/history/get";
        Log.v(TAG, "url "+url);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "onResponse "+response);
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.v(TAG, response);

                        groupData = new ArrayList<>();
                        Map<String, List<String>> childData = new HashMap<>();

                        // Adding Group Data
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                Log.v(TAG, "즐겨찾기 유무" + jsonObject.getString("star"));
                                groupData.add(jsonObject.getString("s_station") + " → " + jsonObject.getString("l_station"));

                                // Adding Child Data
                                List<String> city = new ArrayList<>();
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    city.add(j+"");
                                }
                                childData.put(jsonObject.getString("s_station"), city);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter = new HistoryExpandableListAdapter(getContext(), groupData, childData);

                        Log.v(TAG, expandableListView == null ? "Null" : "Not Null");

                        expandableListView.setAdapter(adapter);
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

                return params;
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(request);


        /*** 익스팬더블 리스트뷰 완성하기 ***/
/*        List<String> provData = new ArrayList<>();
        Map<String, List<String>> cityData = new HashMap<>();

        // Adding Group Data
        for (int i = 0; i < history.length; i++) {
            provData.add(history[i]);

            // Adding Child Data
            List<String> city = new ArrayList<>();
            for (int j = 0; j < route[i].length; j++) {
                city.add(route[i][j]);
            }
            cityData.put(history[i], city);
        }

        HistoryExpandableListAdapter adapter = new HistoryExpandableListAdapter(getContext(), provData, cityData);

        Log.v(TAG, expandableListView == null ? "Null" : "Not Null");

        expandableListView.setAdapter(adapter);*/
        /************************************/


        // 즐겨찾기 설정할 때
        // @ref http://kitesoft.tistory.com/68
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 오래 누르면 즐겨찾기 해제 팝업창이 떠야함
                PopupMenu popupMenu = new PopupMenu(getContext(), view);

                final int index = position;
                // Popup Menu의 MenuItem을 클릭하는 것을 감지하는 Listener 설정
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.fav_insert:
                                update_fav(true);
                                break;

                            case R.id.fav_delete:
                                update_fav(false);
                                break;

                            case R.id.his_remove:
                                delete_fav();
                                // 즐겨찾기를 해제하면 data 배열에서 해당 아이템 빼고 새로고침
                                groupData.remove(index);
                                // gridView 선택 초기화
                                expandableListView.clearChoices();
                                // gridview 갱신
                                adapter.notifyDataSetChanged();
                                break;
                        }

                        return false;
                    }
                });

                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.his_popup, popupMenu.getMenu());
                popupMenu.show();

                // return true to indicate that you have handled the event and it should stop here;
                // return false if you have not handled it and/or the event should continue to any other on-click listeners.
                return true;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void get_history() {
        String url = HOST + "/add/history/get";
        Log.v(TAG, "url "+url);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "onResponse "+response);
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

                return params;
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(request);
    }

    protected void init(View view) {
        result = getLayoutInflater().inflate(R.layout.fragment_result, null, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandablelistview_history);

        //Log.v(TAG, String.valueOf(result.findViewById(R.id.btn_timetable)));
    }

    private void update_fav(final boolean type) {
        String url = HOST + "/add/favorite/star";
        Log.v(TAG, "url "+url);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "onResponse "+response);
                        Toast.makeText(getContext(), "즐겨찾기가 등록/해제되었어요", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "error "+error);
                Toast.makeText(getContext(), "즐겨찾는 경로 설정 실패ㅠㅠ", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", setting.getString("token", null));
                //params.put("id", getHistoryId());
                params.put("type", type ? "1" : "0");

                return params;
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(request);

    }

    private void delete_fav() {
        String url = HOST + "/add/history/delete";
        Log.v(TAG, "url "+url);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "onResponse "+response);
                        Toast.makeText(getContext(), "검색기록이 삭제되었어요", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v(TAG, "error "+error);
                Toast.makeText(getContext(), "검색기록 삭제 실패ㅠㅠ", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", setting.getString("token", null));
                //params.put("id", getHistoryId());

                return params;
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(request);

    }
}

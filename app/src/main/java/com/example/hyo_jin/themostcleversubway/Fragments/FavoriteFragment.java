package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hyo_jin.themostcleversubway.Activity.ResultActivity;
import com.example.hyo_jin.themostcleversubway.Adapter.FavoriteGridAdapter;
import com.example.hyo_jin.themostcleversubway.Item.FavoriteGridItem;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FavoriteFragment extends Fragment {

    final String TAG = "FavoriteFragment";
    private static final String HOST = "http://54.180.32.17:3000";

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    private GridView gridView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        Fragment fragment;

        gridView = (GridView) view.findViewById(R.id.gridview_fav);

        setting = getActivity().getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();

        /*** 그리드뷰 완성하기 ***/
        final List<FavoriteGridItem> data = new ArrayList<>();
        FavoriteGridItem one = new FavoriteGridItem("구파발", "태릉입구", true, "3", "수서", "02 : 45", "8-4", "6-1");
        FavoriteGridItem two = new FavoriteGridItem("태릉입구", "구파발", true, "6", "응암순환", "01 : 05", "10-4", "3-2");
        FavoriteGridItem three = new FavoriteGridItem("숭실대입구", "태릉입구", true, "7", "몰라", "06 : 02", "6-4", "6-1");
        FavoriteGridItem four = new FavoriteGridItem("구파발", "신촌", true, "3", "수서", "02 : 45", "6-4", "6-1");

        data.add(one);
        data.add(two);
        data.add(three);
        data.add(four);

        final FavoriteGridAdapter adapter = new FavoriteGridAdapter(this.getContext(), R.layout.gridlayout_favorite, data);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(FavoriteFragment.super.getContext(), ResultActivity.class);
                intent.putExtra("station_dep", ((FavoriteGridItem) adapterView.getItemAtPosition(position)).getStation1());
                intent.putExtra("station_arr", ((FavoriteGridItem) adapterView.getItemAtPosition(position)).getStation2());
                startActivity(intent);
                //Toast.makeText(getActivity(), ((FavoriteGridItem)adapterView.getItemAtPosition(position)).getStation1(), Toast.LENGTH_SHORT).show();
            }
        });

        // 즐겨찾기 설정할 때
        // @ref http://kitesoft.tistory.com/68
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 오래 누르면 즐겨찾기 해제 팝업창이 떠야함
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                final int index = position;

                // Popup Menu의 MenuItem을 클릭하는 것을 감지하는 Listener 설정
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        update_fav();

                        // 즐겨찾기를 해제하면 data 배열에서 해당 아이템 빼고 새로고침
                        data.remove(index);
                        // gridView 선택 초기화
                        gridView.clearChoices();
                        // gridview 갱신
                        adapter.notifyDataSetChanged();

                        return false;
                    }
                });

                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.fav_popup, popupMenu.getMenu());
                popupMenu.show();

                // return true to indicate that you have handled the event and it should stop here;
                // return false if you have not handled it and/or the event should continue to any other on-click listeners.
                return true;
            }
        });


        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //getActivity().getMenuInflater().inflate(R.menu.fav_popup, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("즐겨찾기 등록/삭제");
    }

    private void update_fav() {
        String url = HOST + "/add/favorite/star";
        Log.v(TAG, "url "+url);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "onResponse "+response);
                        Toast.makeText(getContext(), "즐겨찾기가 해제되었어요", Toast.LENGTH_SHORT).show();
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

                return params;
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(request);

    }
}

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hyo_jin.themostcleversubway.Activity.TimetablePopup;
import com.example.hyo_jin.themostcleversubway.DB.StationDBHelper;
import com.example.hyo_jin.themostcleversubway.R;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        init(view);

        selectDataInDB(); // 데이터베이스에서 지하철역 정보 가져오기

        btn_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "시간표 액티비티를 띄워봄", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SearchFragment.super.getContext(), TimetablePopup.class));
            }
        });

        return view;
    }

    /*** DB에서 데이터 가져와서 리스트에 추가하기 ***/
    protected void selectDataInDB() {
        StationDBHelper dbHelper = new StationDBHelper(getActivity(), null);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        List<String> linenum = new ArrayList<>(); // 선택기준 리스트뷰 리스트
        List<String> stationname = new ArrayList<>(); // 지하철역 리스트뷰 리스트

        Cursor result = dbHelper.select(database, false);
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

        result = dbHelper.select(database, "3", false);
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

    // 컴포넌트 초기화
    protected void init(View view) {

        edit_dep = (EditText) view.findViewById(R.id.edit_arr);
        edit_arr = (EditText) view.findViewById(R.id.edit_dep);

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

    // btn_replace 누르면 출발역 도착역 바꾸기
    protected void swap_station() {

    }
/*
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Create", Toast.LENGTH_LONG).show();

                StationDBHelper dbHelper = new StationDBHelper(getActivity());
                dbHelper.getReadableDatabase();
                dbHelper.close();
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StationDBHelper dbHelper = new StationDBHelper(getActivity());
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                dbHelper.insert(database, "HyoJin", 24, "010-9993-1114");
                dbHelper.close();

                Toast.makeText(getActivity(), "Insert", Toast.LENGTH_LONG).show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Delete", Toast.LENGTH_LONG).show();

                StationDBHelper dbHelper = new StationDBHelper(getActivity());
                dbHelper.getReadableDatabase();
                dbHelper.close();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StationDBHelper dbHelper = new StationDBHelper(getActivity());
                SQLiteDatabase database = dbHelper.getReadableDatabase();

                String result = dbHelper.selectAll(database);

                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            }
        });*/
}

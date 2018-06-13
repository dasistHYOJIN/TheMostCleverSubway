package com.example.hyo_jin.themostcleversubway.Fragments;

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
import android.widget.ListView;
import android.widget.TextView;

import com.example.hyo_jin.themostcleversubway.DB.StationDBHelper;
import com.example.hyo_jin.themostcleversubway.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * This is SubFragment of SearchFragment
 * selectDataInDB() : DB에서 데이터 가져와서 리스트에 추가하기
 */

public class SubSearch3Fragment extends Fragment {
    private static final String TAG = "SubSearch1Fragment";

    private ListView list_lineNum, list_station;

    private JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_search3, container, false);
        init(view);

        // 지하철역 초기는 1호선으로
        selectDataInDB("1"); // 데이터베이스에서 지하철역 정보 가져오기

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

                //edit_dep.setText(station);

                break;
            default:
                break;

        }
    }

    public void myOnClick(View v) {
        switch (v.getId()) {
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
                //String station_name = result.getString(1);
                String station_name = result.getString(0);

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

        list_lineNum = (ListView) view.findViewById(R.id.list_lineNum);
        list_station = (ListView) view.findViewById(R.id.list_station);

        ((TextView) view.findViewById(R.id.list_title1)).setText("호선");
        ((TextView) view.findViewById(R.id.list_title2)).setText("지하철역");

    }
}

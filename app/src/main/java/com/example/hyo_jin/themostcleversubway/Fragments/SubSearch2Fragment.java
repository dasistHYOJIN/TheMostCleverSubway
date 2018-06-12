package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * This is SubFragment of SearchFragment
 *
 * Communicate with MainActivity and Search Fragment
 */
public class SubSearch2Fragment extends Fragment {
    private static final String TAG = "SubSearch2Fragment";

    private ListView list_lineABC, list_stationABC;

    // Define an Interface
    private OnStationSelectedListener mCallback;

    public interface OnStationSelectedListener {
        void onStationSelected(String station);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStationSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStationSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_search2, container, false);
        init(view);
        // 지하철역 초기는 1호선으로
        selectDataInDB("ㄱ"); // 데이터베이스에서 지하철역 정보 가져오기

        list_lineABC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                myOnItemClick(adapterView, view, position, id);
            }
        });

        list_stationABC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                myOnItemClick(adapterView, view, position, id);
            }
        });


        return view;
    }

    public void myOnItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (adapterView.getId()) {
            case R.id.list_lineABC :
                String lineABC = adapterView.getItemAtPosition(position).toString();
                StationDBHelper dbHelper = new StationDBHelper(getActivity(), null);

                setStationList(dbHelper, lineABC);

                break;
            case R.id.list_stationABC :
                String station = adapterView.getItemAtPosition(position).toString();

                Log.v(TAG, station);

                // Send the event to the host activity
                mCallback.onStationSelected(station);

                break;
            default:
                break;
        }
    }

    /*** DB에서 데이터 가져와서 리스트에 추가하기 ***/
    protected void selectDataInDB(String lineABC) {
        StationDBHelper dbHelper = new StationDBHelper(getActivity(), null);

        setLineList();
        setStationList(dbHelper, lineABC);

        dbHelper.close();
    }

    public void setLineList() {
        List<String> lineABC = new ArrayList<>(); // 선택기준 리스트뷰 리스트

        lineABC.add("ㄱ");
        lineABC.add("ㄴ");
        lineABC.add("ㄷ");
        lineABC.add("ㄹ");
        lineABC.add("ㅁ");
        lineABC.add("ㅂ");
        lineABC.add("ㅅ");
        lineABC.add("ㅇ");
        lineABC.add("ㅈ");
        lineABC.add("ㅊ");
        lineABC.add("ㅋ");
        lineABC.add("ㅌ");
        lineABC.add("ㅍ");
        lineABC.add("ㅎ");

        // 리스트뷰에 리스트 추가
        ArrayAdapter<String> adapter_lineABC = new ArrayAdapter<String>(getContext(), R.layout.listlayout_linenum, R.id.text_subwayvalue, lineABC);
        list_lineABC.setAdapter(adapter_lineABC);
    }

    public void setStationList(StationDBHelper dbHelper, String lineABC) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<String> stationname = new ArrayList<>(); // 지하철역 리스트뷰 리스트

        Cursor result = dbHelper.selectRightList(database, lineABC, true);
        if(result.moveToFirst()){
            while (!result.isAfterLast()) {
                String station_name = result.getString(1);
                stationname.add(station_name);

                result.moveToNext();
            }
            result.close();
        }
        else {
            stationname.add(lineABC + "으로 시작하는 지하철역이 없어요!");
        }
        ArrayAdapter<String> adapter_linename = new ArrayAdapter<String>(getContext(), R.layout.listlayout_linenum, R.id.text_subwayvalue, stationname);
        list_stationABC.setAdapter(adapter_linename);
    }

    // 컴포넌트 초기화
    protected void init(View view) {
        list_lineABC = (ListView) view.findViewById(R.id.list_lineABC);
        list_stationABC = (ListView) view.findViewById(R.id.list_stationABC);

        ((TextView) view.findViewById(R.id.list_title1)).setText("기준");
        ((TextView) view.findViewById(R.id.list_title2)).setText("지하철역");

    }
}

package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.Toast;

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
    private static final String TAG = "SubSearch3Fragment";

    private ListView list_lineNum, list_station;

    // Define an Interface
    private SubSearch3Fragment.OnStationSelectedListener mCallback;

    public interface OnStationSelectedListener {
        void onStationSelected(String station);
    }

    // 프래그먼트가 처음으로 액티비티에 부착될 때 호출됨
    // 시작단계에서 가장 먼저 어떤 액티비티에 붙을 것인지 결정
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (SubSearch3Fragment.OnStationSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStationSelectedListener");
        }
    }

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

                // 선택한 기준(가나다순이거나 호선순)에 색칠하기
                for (int i = 0; i < adapterView.getChildCount(); i++) {
                    if(position == i )
                        adapterView.getChildAt(i).setBackgroundColor(Color.YELLOW);
                    else
                        adapterView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }

                // 역 리스트 뿌리기
                setStationList(dbHelper, lineNum);

                break;
            case R.id.list_station :
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

                switch (line_num) {
                    case "UI" :
                        line_num = "우이선";
                        break;
                    case "A" :
                        line_num = "공항";
                        break;
                    case "S" :
                        line_num = "우이선";
                        break;
                    case "I" :
                        line_num = "인천1호선";
                        break;
                    case "I2" :
                        line_num = "인천2호선";
                        break;
                    case "K" :
                        line_num = "경의중앙선";
                        break;
                    case "B" :
                        line_num = "분당선";
                        break;
                    case "SU" :
                        line_num = "수인선";
                        break;
                    case "KK" :
                        line_num = "경강선";
                        break;
                    case "G" :
                        line_num = "경춘선";
                        break;
                    case "T" :
                        line_num = "테스트";
                        break;
                    case "U" :
                        line_num = "의정부";
                        break;
                    case "E" :
                        line_num = "에버라인";
                        break;
                    default:
                        line_num += "호선";
                        break;
                }

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

        String line_num;
        switch (lineNum) {
            case "우이선" :
                line_num = "UI";
                break;
            case "공항" :
                line_num = "A";
                break;
            case "S" :
                line_num = "우이선";
                break;
            case "인천1호선" :
                line_num = "I";
                break;
            case "인천2호선" :
                line_num = "I2";
                break;
            case "경의중앙선" :
                line_num = "K";
                break;
            case "분당선" :
                line_num = "B";
                break;
            case "수인선" :
                line_num = "SU";
                break;
            case "경강선" :
                line_num = "KK";
                break;
            case "경춘선" :
                line_num = "G";
                break;
            case "테스트" :
                line_num = "T";
                break;
            case "의정부" :
                line_num = "U";
                break;
            case "에버라인" :
                line_num = "E";
                break;
            default:
                line_num = lineNum.substring(0, 1);
                break;
        }

        Cursor result = dbHelper.selectRightList(database, line_num, false);
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

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
 */
public class SubSearch1Fragment extends Fragment {
    private static final String TAG = "SubSearch1Fragment";

    private ListView list_lineNum, list_station;

    private JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_search1, container, false);
        init(view);

        return view;
    }


    // 컴포넌트 초기화
    protected void init(View view) {

    }
}

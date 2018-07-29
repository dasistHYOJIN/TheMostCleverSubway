package com.example.hyo_jin.themostcleversubway.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hyo_jin.themostcleversubway.R;
import com.github.chrisbanes.photoview.PhotoView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * This is SubFragment of SearchFragment
 * PhotoView(Pinch to Zoom) : https://github.com/chrisbanes/PhotoView
 */
public class SubSearch1Fragment extends Fragment {
    private static final String TAG = "SubSearch1Fragment";

    private PhotoView photoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_search1, container, false);
        init(view);

        try {
            photoView.setImageResource(R.drawable.subway);
            photoView.setBackgroundColor(Color.BLACK);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        /*photoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                Log.v(TAG, "x : " + x + "\ty : " + y);
                return false;
            }
        });*/


        return view;
    }

    // 컴포넌트 초기화
    protected void init(View view) {
        //subway = view.findViewById(R.id.subway);
        photoView = view.findViewById(R.id.photo_view);

    }
}

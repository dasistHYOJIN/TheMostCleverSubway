package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hyo_jin.themostcleversubway.Activity.SettingsActivity;
import com.example.hyo_jin.themostcleversubway.Activity.TimetablePopup;
import com.example.hyo_jin.themostcleversubway.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment//.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment//#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private ImageButton btn_search;
    private Button btn_map, btn_abc, btn_line;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        btn_search = (ImageButton) view.findViewById(R.id.btn_search);

        btn_map = (Button) view.findViewById(R.id.btn_map);
        btn_abc = (Button) view.findViewById(R.id.btn_abc);
        btn_line = (Button) view.findViewById(R.id.btn_line);
        btn_map.setText("노선도");
        btn_abc.setText("가나다");
        btn_line.setText("호선순");

        btn_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "시간표 액티비티를 띄워봄", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SearchFragment.super.getContext(), TimetablePopup.class));
            }
        });

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}

package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.hyo_jin.themostcleversubway.Activity.ResultActivity;
import com.example.hyo_jin.themostcleversubway.Activity.SettingsActivity;
import com.example.hyo_jin.themostcleversubway.Adapter.FavoriteGridAdapter;
import com.example.hyo_jin.themostcleversubway.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteFragment//.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteFragment//#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    private GridView gridView;
    private Button btn_test;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        Fragment fragment;

        gridView = (GridView) view.findViewById(R.id.gridview_fav);
        FavoriteGridAdapter favoriteGridAdapter = new FavoriteGridAdapter(this.getContext());

        btn_test = (Button) view.findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragment = new FavoriteFragment();
                startActivity(new Intent(FavoriteFragment.super.getContext(), ResultActivity.class));
            }
        });

        return view;
    }
}

package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hyo_jin.themostcleversubway.Activity.ResultActivity;
import com.example.hyo_jin.themostcleversubway.Activity.SettingsActivity;
import com.example.hyo_jin.themostcleversubway.Adapter.FavoriteGridAdapter;
import com.example.hyo_jin.themostcleversubway.Adapter.HistoryListAdapter;
import com.example.hyo_jin.themostcleversubway.Item.FavoriteGridItem;
import com.example.hyo_jin.themostcleversubway.Item.HistoryListItem;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.List;

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

        /*** 그리드뷰 완성하기 ***/
        List<FavoriteGridItem> data = new ArrayList<>();
        FavoriteGridItem one = new FavoriteGridItem("구파발", "태릉입구", true, "3", "수서", "02 : 45", "8-4", "6-1");
        FavoriteGridItem two = new FavoriteGridItem("태릉입구", "구파발", true, "6", "응암순환", "01 : 05", "10-4", "3-2");
        FavoriteGridItem three = new FavoriteGridItem("숭실대입구", "태릉입구", true, "7", "몰라", "06 : 02", "6-4", "6-1");
        FavoriteGridItem four = new FavoriteGridItem("구파발", "신촌", true, "3", "수서", "02 : 45", "6-4", "6-1");

        data.add(one);
        data.add(two);
        data.add(three);
        data.add(four);

        FavoriteGridAdapter adapter = new FavoriteGridAdapter(this.getContext(), R.layout.gridlayout_favorite, data);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                startActivity(new Intent(FavoriteFragment.super.getContext(), ResultActivity.class));
                Toast.makeText(getActivity(), ((FavoriteGridItem)adapterView.getItemAtPosition(position)).getStation1(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

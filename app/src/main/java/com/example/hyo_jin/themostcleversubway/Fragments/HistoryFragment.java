package com.example.hyo_jin.themostcleversubway.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hyo_jin.themostcleversubway.Adapter.HistoryListAdapter;
import com.example.hyo_jin.themostcleversubway.Item.HistoryListItem;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment//.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment//#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    private static final String TAG = "HistoryFragment";
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        listView = (ListView) view.findViewById(R.id.listview_history);

        /*** 리스트뷰 완성하기 ***/
        List<HistoryListItem> data = new ArrayList<>();
        HistoryListItem one = new HistoryListItem("구파발", "태릉입구", true);
        HistoryListItem two = new HistoryListItem("태릉입구", "구파발", true);
        HistoryListItem three = new HistoryListItem("숭실대입구", "태릉입구", true);
        HistoryListItem four = new HistoryListItem("구파발", "신촌", true);

        data.add(one);
        data.add(two);
        data.add(three);
        data.add(four);

        HistoryListAdapter adapter = new HistoryListAdapter(this.getContext(), R.layout.listlayout_history, data);

        listView.setAdapter(adapter);

        Log.v(TAG, "1");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.v(TAG, "2");

                Toast.makeText(getActivity().getApplicationContext(), ((HistoryListItem)adapterView.getItemAtPosition(position)).getStation1(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.v(TAG, "3");

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

}
